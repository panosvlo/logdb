package gr.uoa.di.cs.logdb.dto;

import java.util.Date;

public class LogCountPerDayDTO {
    private Date logDate;
    private Long totalLogs;

    public LogCountPerDayDTO(Date logDate, Long totalLogs) {
        this.logDate = logDate;
        this.totalLogs = totalLogs;
    }

    // Getters and setters
    public Date getLogDate() {
        return logDate;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    public Long getTotalLogs() {
        return totalLogs;
    }

    public void setTotalLogs(Long totalLogs) {
        this.totalLogs = totalLogs;
    }
}
