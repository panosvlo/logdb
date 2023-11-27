package gr.uoa.di.cs.logdb.dto;

public class IpRequestCountDTO {
    private String sourceIp;
    private int requestCount;

    public IpRequestCountDTO(String sourceIp, int requestCount) {
        this.sourceIp = sourceIp;
        this.requestCount = requestCount;
    }

    // Getter for sourceIp
    public String getSourceIp() {
        return sourceIp;
    }

    // Setter for sourceIp
    public void setSourceIp(String sourceIp) {
        this.sourceIp = sourceIp;
    }

    // Getter for requestCount
    public int getRequestCount() {
        return requestCount;
    }

    // Setter for requestCount
    public void setRequestCount(int requestCount) {
        this.requestCount = requestCount;
    }
}

