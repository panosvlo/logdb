package gr.uoa.di.cs.logdb.dto;

import java.util.Date;

public class AccessLogDTO {
    private Long id;
    private Long logTypeId;
    private Date timestamp;
    private String sourceIp;
    private String logDetails;

    public AccessLogDTO(Long id, Long logTypeId, Date timestamp, String sourceIp, String logDetails) {
        this.id = id;
        this.logTypeId = logTypeId;
        this.timestamp = timestamp;
        this.sourceIp = sourceIp;
        this.logDetails = logDetails;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public Long getLogTypeId() {
        return logTypeId;
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

    public void setLogTypeId(Long logTypeId) {
        this.logTypeId = logTypeId;
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
