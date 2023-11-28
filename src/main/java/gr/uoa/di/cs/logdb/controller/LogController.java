package gr.uoa.di.cs.logdb.controller;

import gr.uoa.di.cs.logdb.dto.*;
import gr.uoa.di.cs.logdb.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/logs")
public class LogController {

    @Autowired
    private LogRepository logRepository;

    @GetMapping("/countByType")
    public ResponseEntity<List<LogCountDTO>> getLogCountByType(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        List<LogCountDTO> counts = logRepository.findLogCountByTypeAndDateRange(startDate, endDate);
        return ResponseEntity.ok(counts);
    }
    @GetMapping("/total-per-day")
    public ResponseEntity<List<LogCountPerDayDTO>> getTotalLogsPerDayForActionType(
            @RequestParam String method,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {

        List<Object[]> queryResults = logRepository.findTotalLogsPerDayForActionType(method, startDate, endDate);
        List<LogCountPerDayDTO> results = new ArrayList<>();

        for (Object[] result : queryResults) {
            Date logDate = (Date) result[0];
            Long totalLogs = ((Number) result[1]).longValue();
            results.add(new LogCountPerDayDTO(logDate, totalLogs));
        }

        return ResponseEntity.ok(results);
    }

    @GetMapping("/mostCommonLog")
    public ResponseEntity<List<MostCommonLogDTO>> getMostCommonLogByDate(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date specificDate) {
        List<MostCommonLogDTO> result = logRepository.findMostCommonLogByDate(specificDate);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/topBlockActions")
    public ResponseEntity<List<TopBlockActionsDTO>> getTopBlockActions(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        PageRequest pageRequest = PageRequest.of(0, 5); // Fetch the top 5 results
        Page<Object[]> page = logRepository.findTopBlockActionsBetweenDates(startDate, endDate, pageRequest);

        List<TopBlockActionsDTO> results = page.getContent().stream()
                .map(obj -> new TopBlockActionsDTO((String) obj[0], (Date) obj[1], ((BigInteger) obj[2]).longValue()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(results);
    }

    @GetMapping("/secondMostCommonResource")
    public ResponseEntity<SecondMostCommonResourceDTO> getSecondMostCommonResource() {
        List<Object[]> rawResult = logRepository.findSecondMostCommonResourceRaw();
        if (rawResult == null || rawResult.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Object[] result = rawResult.get(0);
        SecondMostCommonResourceDTO dto = new SecondMostCommonResourceDTO(
                (String) result[0],
                ((Number) result[1]).longValue()
        );

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/accessLogsSize")
    public ResponseEntity<List<AccessLogDTO>> getAccessLogsWithSizeLessThan(@RequestParam int size) {
        List<Object[]> rawLogs = logRepository.findAccessLogsWithSizeLessThanRaw(size);
        List<AccessLogDTO> logs = rawLogs.stream().map(obj -> {
            Long id = ((Number) obj[0]).longValue(); // Cast to Number and then get long value
            Long logTypeId = ((Number) obj[1]).longValue(); // Cast to Number and then get long value
            Date timestamp = (Date) obj[2];
            String sourceIp = (String) obj[3];
            String logDetails = (String) obj[4];
            return new AccessLogDTO(id, logTypeId, timestamp, sourceIp, logDetails); // Set destinationIp as null
        }).collect(Collectors.toList());

        return ResponseEntity.ok(logs);
    }
    @GetMapping("/accessLogs/firefox")
    public ResponseEntity<List<Object[]>> getAccessLogsWithFirefox() {
        List<Object[]> logs = logRepository.findAccessLogsByFirefoxRaw();
        return ResponseEntity.ok(logs);
    }

    @GetMapping("/methodUsage")
    public ResponseEntity<List<MethodUsageDTO>> getMethodUsage(
            @RequestParam String httpMethod,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = sdf.parse(startDate);
            Date end = sdf.parse(endDate);
            List<MethodUsageDTO> results = logRepository.findMethodUsage(httpMethod, start, end);
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/ipsWithTwoMethods")
    public ResponseEntity<List<IpRequestCountDTO>> getIPsWithTwoMethods(
            @RequestParam String method1,
            @RequestParam String method2,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {

        List<Object[]> results = logRepository.findIPsWithTwoMethods(method1, method2, startDate, endDate);
        List<IpRequestCountDTO> ipRequestCounts = results.stream()
                .map(result -> new IpRequestCountDTO((String) result[0], ((Number) result[1]).intValue()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(ipRequestCounts);
    }

    @GetMapping("/distinctMethods")
    public ResponseEntity<List<IpMethodCountDTO>> getIPsWithDistinctMethods(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
            @RequestParam int minMethods) {

        List<Object[]> queryResults = logRepository.findIPsWithDistinctMethods(startDate, endDate, minMethods);
        List<IpMethodCountDTO> results = queryResults.stream()
                .map(obj -> new IpMethodCountDTO((String) obj[0], ((Number) obj[1]).intValue()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(results);
    }
    @GetMapping("/referrers/multiple-resources")
    public ResponseEntity<List<ReferrerResourceCountDTO>> getReferrersWithMultipleResources() {
        List<Object[]> rawResults = logRepository.findReferrersWithMultipleResourcesRaw();
        List<ReferrerResourceCountDTO> results = rawResults.stream()
                .map(result -> new ReferrerResourceCountDTO(
                        (String) result[0],
                        ((Number) result[1]).intValue())
                )
                .collect(Collectors.toList());
        return ResponseEntity.ok(results);
    }
    @GetMapping("/blocks")
    public ResponseEntity<List<BlockAllocationDTO>> getBlockAllocationsAndReplications() {
        List<Object[]> queryResults = logRepository.findBlockAllocationsAndReplications();
        List<BlockAllocationDTO> blocks = queryResults.stream().map(result -> new BlockAllocationDTO(
                (String) result[0],
                ((Timestamp) result[1]).toLocalDateTime(),
                ((Timestamp) result[2]).toLocalDateTime()
        )).collect(Collectors.toList());

        if (blocks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(blocks);
    }
    @GetMapping("/blockAllocationsAndReplicationsSameHour")
    public ResponseEntity<List<Object[]>> getBlockAllocationsAndReplicationsSameHour() {
        List<Object[]> data = logRepository.findBlockAllocationsAndReplicationsSameHour();
        return ResponseEntity.ok(data);
    }
}