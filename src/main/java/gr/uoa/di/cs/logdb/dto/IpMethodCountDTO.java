package gr.uoa.di.cs.logdb.dto;

public class IpMethodCountDTO {
    private String sourceIp;
    private int distinctMethodsCount;

    public IpMethodCountDTO(String sourceIp, int distinctMethodsCount) {
        this.sourceIp = sourceIp;
        this.distinctMethodsCount = distinctMethodsCount;
    }

    public String getSourceIp() {
        return sourceIp;
    }

    public void setSourceIp(String sourceIp) {
        this.sourceIp = sourceIp;
    }

    public int getDistinctMethodsCount() {
        return distinctMethodsCount;
    }

    public void setDistinctMethodsCount(int distinctMethodsCount) {
        this.distinctMethodsCount = distinctMethodsCount;
    }
}
