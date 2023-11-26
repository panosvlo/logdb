package gr.uoa.di.cs.logdb.dto;

public class MethodUsageDTO {
    private String sourceIp;
    private Long requestCount;

    public MethodUsageDTO(String sourceIp, Long requestCount) {
        this.sourceIp = sourceIp;
        this.requestCount = requestCount;
    }

    // Getters and Setters
    public String getSourceIp() {
        return sourceIp;
    }

    public void setSourceIp(String sourceIp) {
        this.sourceIp = sourceIp;
    }

    public Long getRequestCount() {
        return requestCount;
    }

    public void setRequestCount(Long requestCount) {
        this.requestCount = requestCount;
    }
}
