package gr.uoa.di.cs.logdb.dto;

import java.util.Date;

public class LogSearchDTO {
    private Long id;
    private String logType;
    private Date timestamp;
    private String sourceIp;
    private String logDetails;

    // Constructor
    public LogSearchDTO(Long id, String logType, Date timestamp, String sourceIp, String logDetails) {
        this.id = id;
        this.logType = logType;
        this.timestamp = timestamp;
        this.sourceIp = sourceIp;
        this.logDetails = logDetails;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getLogType() {
        return logType;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getSourceIp() {
        return sourceIp;
    }

    public String getLogDetails() {
        return logDetails;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setSourceIp(String sourceIp) {
        this.sourceIp = sourceIp;
    }

    public void setLogDetails(String logDetails) {
        this.logDetails = logDetails;
    }
}
