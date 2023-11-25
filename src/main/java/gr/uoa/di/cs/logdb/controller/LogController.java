package gr.uoa.di.cs.logdb.controller;

import gr.uoa.di.cs.logdb.dto.LogCountDTO;
import gr.uoa.di.cs.logdb.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

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
}
