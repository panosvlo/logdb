package gr.uoa.di.cs.logdb.util;

import gr.uoa.di.cs.logdb.model.Log;
import gr.uoa.di.cs.logdb.model.LogDetail;
import gr.uoa.di.cs.logdb.model.LogType;
import gr.uoa.di.cs.logdb.repository.LogDetailRepository;
import gr.uoa.di.cs.logdb.repository.LogRepository;
import gr.uoa.di.cs.logdb.repository.LogTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class LogFileParser {

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private LogTypeRepository logTypeRepository;

    @Autowired
    private LogDetailRepository logDetailRepository;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z");
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH);

    public void parseAndLoadLogFile(MultipartFile file, Integer logTypeId) throws Exception {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            switch (logTypeId) {
                case 1:
                    parseAccessLogs(reader);
                    break;
                case 2:
                    parseHDFSDataXceiverLogs(reader);
                    break;
                case 3:
                    parseHDFSNamesystemLogs(reader);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid log type ID");
            }
        }
    }

    private void parseAccessLogs(BufferedReader reader) throws Exception {
        final Pattern accessLogPattern = Pattern.compile("^(\\S+) \\S+ \\S+ \\[(.+?)\\] \"(\\S+) (\\S+)? (\\S+)\" (\\d{3}) (\\d+|-) \"(.*?)\" \"(.*?)\"");
        LogType accessLogType = logTypeRepository.findByTypeName("access_log");
        String line;
        while ((line = reader.readLine()) != null) {
            Matcher matcher = accessLogPattern.matcher(line);
            if (matcher.find()) {
                // Extract data
                String ip = matcher.group(1);
                ZonedDateTime zonedDateTime = ZonedDateTime.parse(matcher.group(2), dateFormatter);
                // Convert ZonedDateTime to Date
                Date timestamp = Date.from(zonedDateTime.toInstant());

                String method = matcher.group(3);
                String resource = matcher.group(4);
                String protocol = matcher.group(5);
                int statusCode = Integer.parseInt(matcher.group(6));
                int size = matcher.group(7).equals("-") ? 0 : Integer.parseInt(matcher.group(7));
                String referer = matcher.group(8).equals("-") ? null : matcher.group(8);
                String userAgent = matcher.group(9);

                // Save log entry
                Log log = new Log();
                log.setLogTypeId(accessLogType.getId());
                log.setSourceIp(ip);
                log.setTimestamp(timestamp);
                log.setDestinationIp(null); // No destination IP in access logs
                log = logRepository.save(log);

                // Save log details
                saveLogDetail(log.getId(), "method", method);
                saveLogDetail(log.getId(), "resource", resource);
                saveLogDetail(log.getId(), "protocol", protocol);
                saveLogDetail(log.getId(), "status_code", String.valueOf(statusCode));
                if (size > 0) {
                    saveLogDetail(log.getId(), "size", String.valueOf(size));
                }
                if (referer != null) {
                    saveLogDetail(log.getId(), "referer", referer);
                }
                saveLogDetail(log.getId(), "user_agent", userAgent);
            }
        }
    }

    private void parseHDFSDataXceiverLogs(BufferedReader reader) throws Exception {
        final Pattern hdfsDataXceiverLogPattern = Pattern.compile(
                "(\\d{6} \\d{6}) (\\d+) INFO dfs\\.DataNode\\$DataXceiver: (\\S+) block blk_(-?\\d+) src: (/\\d+.\\d+.\\d+.\\d+:\\d+) dest: (/\\d+.\\d+.\\d+.\\d+:\\d+)"
        );
        String line;
        final DateTimeFormatter hdfsDateFormatter = DateTimeFormatter.ofPattern("yyMMdd HHmmss");
        LogType hdfsDataXceiverLogType = logTypeRepository.findByTypeName("hdfs_dataxceiver_log");
        while ((line = reader.readLine()) != null) {
            Matcher matcher = hdfsDataXceiverLogPattern.matcher(line);
            if (matcher.find()) {
                // Extract data
                LocalDateTime dateTime = LocalDateTime.parse(matcher.group(1), hdfsDateFormatter);
                Date timestamp = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
                String action = matcher.group(3);
                String blockId = matcher.group(4);
                String src = matcher.group(5);
                String dest = matcher.group(6);

                // Save log entry
                Log log = new Log();
                log.setLogTypeId(hdfsDataXceiverLogType.getId()); // Set log type id
                log.setTimestamp(timestamp);
                log.setSourceIp(src.substring(1, src.indexOf(':'))); // Remove leading slash and port
                log.setDestinationIp(dest.substring(1, dest.indexOf(':'))); // Remove leading slash and port
                log = logRepository.save(log);

                // Save log details
                saveLogDetail(log.getId(), "action", action);
                saveLogDetail(log.getId(), "block_id", blockId);
                saveLogDetail(log.getId(), "src_port", src.substring(src.indexOf(':') + 1));
                saveLogDetail(log.getId(), "dest_port", dest.substring(dest.indexOf(':') + 1));
            }
        }
    }

    private void parseHDFSNamesystemLogs(BufferedReader reader) throws Exception {
        final Pattern hdfsNamesystemLogPattern  = Pattern.compile(
                "(\\d{6} \\d{6}) (\\d+) (\\S+) (dfs\\.FSNamesystem): (BLOCK\\*) (\\S+): (.+)"
        );
        // Pattern specifically for replication log entries
        final Pattern hdfsReplicationLogPattern = Pattern.compile(
                "(\\d{6} \\d{6}) (\\d+) (\\S+) (dfs\\.FSNamesystem): (BLOCK\\*) ask (\\d+.\\d+.\\d+.\\d+:\\d+) to replicate blk_(-?\\d+) to datanode\\(s\\) (.+)"
        );
        String line;
        final DateTimeFormatter hdfsDateFormatter = DateTimeFormatter.ofPattern("yyMMdd HHmmss");
        while ((line = reader.readLine()) != null) {
            Matcher matcher = hdfsNamesystemLogPattern.matcher(line);
            Matcher replicationMatcher = hdfsReplicationLogPattern.matcher(line);
            LogType hdfsNamesystemLogType = logTypeRepository.findByTypeName("hdfs_fs_namesystem_log");
            if (matcher.find()) {
                LocalDateTime dateTime = LocalDateTime.parse(matcher.group(1), hdfsDateFormatter);
                Date timestamp = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
                String threadId = matcher.group(2);
                String logLevel = matcher.group(3);
                String component = matcher.group(4);
                String operation = matcher.group(6);
                String details = matcher.group(7);

                // Assuming the IP and port are always present after the operation text
                Pattern ipPattern = Pattern.compile("(\\d+.\\d+.\\d+.\\d+:\\d+)");
                Matcher ipMatcher = ipPattern.matcher(details);
                String ip = null;
                if (ipMatcher.find()) {
                    ip = ipMatcher.group(1).split(":")[0]; // Get just the IP
                }

                // Save log entry
                Log log = new Log();
                log.setLogTypeId(hdfsNamesystemLogType.getId());
                log.setTimestamp(timestamp);
                log.setSourceIp(ip); // IP is captured from the details
                log.setDestinationIp(null); // No destination IP in these logs
                log = logRepository.save(log);

                // Save log details
                saveLogDetail(log.getId(), "thread_id", threadId);
                saveLogDetail(log.getId(), "log_level", logLevel);
                saveLogDetail(log.getId(), "component", component);
                saveLogDetail(log.getId(), "operation", operation);
                saveLogDetail(log.getId(), "details", details); // Saves the full details string

                // Now, let's extract and save the block ID and size if present
                Pattern blockPattern = Pattern.compile("blk_(-?\\d+)");
                Matcher blockMatcher = blockPattern.matcher(details);
                if (blockMatcher.find()) {
                    saveLogDetail(log.getId(), "block_id", blockMatcher.group(1));
                }

                Pattern sizePattern = Pattern.compile("size (\\d+)");
                Matcher sizeMatcher = sizePattern.matcher(details);
                if (sizeMatcher.find()) {
                    saveLogDetail(log.getId(), "size", sizeMatcher.group(1));
                }
            } else if (replicationMatcher.find()) {
                // Handle replication log entries
                handleReplicationLog(replicationMatcher, hdfsNamesystemLogType, hdfsDateFormatter);
            }
        }
    }

    private void handleReplicationLog(Matcher matcher, LogType logType, DateTimeFormatter dateFormatter) {
        LocalDateTime dateTime = LocalDateTime.parse(matcher.group(1), dateFormatter);
        Date timestamp = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
        String threadId = matcher.group(2);
        String logLevel = matcher.group(3);
        String sourceIp = matcher.group(6).split(":")[0]; // Extract IP from source
        String blockId = matcher.group(7);
        String datanodes = matcher.group(8); // This will capture all datanode IPs

        Log log = new Log();
        log.setLogTypeId(logType.getId());
        log.setTimestamp(timestamp);
        log.setSourceIp(sourceIp); // IP extracted from the replication log
        log.setDestinationIp(null);
        log = logRepository.save(log);

        saveLogDetail(log.getId(), "thread_id", threadId);
        saveLogDetail(log.getId(), "log_level", logLevel);
        saveLogDetail(log.getId(), "operation", "replicate");
        saveLogDetail(log.getId(), "block_id", blockId);
        saveLogDetail(log.getId(), "datanodes", datanodes); // Saves all datanode IPs as a single entry
    }

    private void saveLogDetail(Long logId, String key, String value) {
        LogDetail logDetail = new LogDetail();
        logDetail.setLogId(logId);
        logDetail.setKey(key);
        logDetail.setValue(value);
        logDetailRepository.save(logDetail);
    }
}
