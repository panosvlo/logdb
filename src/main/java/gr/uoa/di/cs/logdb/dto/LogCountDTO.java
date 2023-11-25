package gr.uoa.di.cs.logdb.dto;

public class LogCountDTO {
    private Long logTypeId;
    private Long count;

    public LogCountDTO(Long logTypeId, Long count) {
        this.logTypeId = logTypeId;
        this.count = count;
    }

    // Getters and setters
    public Long getLogTypeId() {
        return logTypeId;
    }

    public void setLogTypeId(Long logTypeId) {
        this.logTypeId = logTypeId;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}