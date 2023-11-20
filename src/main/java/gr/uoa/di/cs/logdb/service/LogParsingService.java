package gr.uoa.di.cs.logdb.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gr.uoa.di.cs.logdb.model.Log;
import gr.uoa.di.cs.logdb.model.LogDetail;
import gr.uoa.di.cs.logdb.model.LogType;
import gr.uoa.di.cs.logdb.repository.LogDetailRepository;
import gr.uoa.di.cs.logdb.repository.LogRepository;
import gr.uoa.di.cs.logdb.repository.LogTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Date;
import java.time.ZoneId;

@Service
public class LogParsingService {

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private LogTypeRepository logTypeRepository;

    @Autowired
    private LogDetailRepository logDetailRepository;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z");

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH);


    @PostConstruct
    public void init() {
        loadAccessLogs();
        loadHDFSNamesystemLogs();
    }

    private void loadAccessLogs() {
        final String accessLogPath = "access_log_full.short";
        final Pattern accessLogPattern = Pattern.compile("^(\\S+) \\S+ \\S+ \\[(.+?)\\] \"(\\S+) (\\S+)? (\\S+)\" (\\d{3}) (\\d+|-) \"(.*?)\" \"(.*?)\"");
        try (BufferedReader br = new BufferedReader(new FileReader(accessLogPath))) {
            String line;
            while ((line = br.readLine()) != null) {
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
        } catch (DateTimeParseException e) {
            System.err.println("Error parsing the date: " + e.getParsedString());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void saveLogDetail(Long logId, String key, String value) {
        LogDetail logDetail = new LogDetail();
        logDetail.setLogId(logId);
        logDetail.setKey(key);
        logDetail.setValue(value);
        logDetailRepository.save(logDetail);
    }
    public void printCurrentWorkingDirectory() {
        String currentWorkingDirectory = System.getProperty("user.dir");
        System.out.println("Current working directory: " + currentWorkingDirectory);
    }

    private void loadHDFSNamesystemLogs() {
        final String hdfsNamesystemLogPath = "HDFS_FS_Namesystem.log.short"; // Adjust the path accordingly
        final Pattern hdfsNamesystemLogPattern = Pattern.compile(
                "(\\d{6} \\d{6}) (\\d+) (\\S+) (dfs\\.FSNamesystem): (BLOCK\\* .+?): (.+)"
        );
        final DateTimeFormatter hdfsDateFormatter = DateTimeFormatter.ofPattern("yyMMdd HHmmss");

        try (BufferedReader br = new BufferedReader(new FileReader(hdfsNamesystemLogPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                Matcher matcher = hdfsNamesystemLogPattern.matcher(line);
                if (matcher.find()) {
                    // Extract data
                    LocalDateTime dateTime = LocalDateTime.parse(matcher.group(1), hdfsDateFormatter);
                    Date timestamp = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
                    String eventType = matcher.group(5);
                    String details = matcher.group(6);

                    // Further extract the block ID from the details string
                    Pattern detailPattern = Pattern.compile("/([^ ]+).*blk_(-?\\d+)");
                    Matcher detailMatcher = detailPattern.matcher(details);
                    String path = null;
                    String blockId = null;
                    if (detailMatcher.find()) {
                        path = detailMatcher.group(1); // Extracted path
                        blockId = detailMatcher.group(2); // Extracted block ID
                    }

                    // Save log entry
                    Log log = new Log();
                    log.setTimestamp(timestamp);
                    // Additional log fields can be set here if needed
                    log = logRepository.save(log);

                    // Save log details
                    saveLogDetail(log.getId(), "event_type", eventType);
                    if (path != null) {
                        saveLogDetail(log.getId(), "path", path);
                    }
                    if (blockId != null) {
                        saveLogDetail(log.getId(), "block_id", blockId);
                    }
                    // Add additional details as needed
                }
            }
        } catch (DateTimeParseException e) {
            System.err.println("Error parsing the date: " + e.getParsedString());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
