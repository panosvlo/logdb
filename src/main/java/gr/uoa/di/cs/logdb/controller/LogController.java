package gr.uoa.di.cs.logdb.controller;

import gr.uoa.di.cs.logdb.dto.LogCountDTO;
import gr.uoa.di.cs.logdb.dto.LogCountPerDayDTO;
import gr.uoa.di.cs.logdb.dto.MostCommonLogDTO;
import gr.uoa.di.cs.logdb.dto.TopBlockActionsDTO;
import gr.uoa.di.cs.logdb.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
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
            @RequestParam String action,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {

        List<Object[]> queryResults = logRepository.findTotalLogsPerDayForActionType(action, startDate, endDate);
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
}