package gr.uoa.di.cs.logdb.dto;

public class MostCommonLogDTO {
    private String sourceIp;
    private String mostCommonLogType;
    private Long total;

    public MostCommonLogDTO(String sourceIp, String mostCommonLogType, Long total) {
        this.sourceIp = sourceIp;
        this.mostCommonLogType = mostCommonLogType;
        this.total = total;
    }

    // Getters and setters
    public String getSourceIp() {
        return sourceIp;
    }

    public void setSourceIp(String sourceIp) {
        this.sourceIp = sourceIp;
    }

    public String getMostCommonLogType() {
        return mostCommonLogType;
    }

    public void setMostCommonLogType(String mostCommonLogType) {
        this.mostCommonLogType = mostCommonLogType;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
