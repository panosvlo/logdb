package gr.uoa.di.cs.logdb.dto;

public class ReferrerResourceCountDTO {

    private String referrer;
    private int distinctResourcesCount;

    public ReferrerResourceCountDTO(String referrer, int distinctResourcesCount) {
        this.referrer = referrer;
        this.distinctResourcesCount = distinctResourcesCount;
    }

    // Getters and Setters
    public String getReferrer() {
        return referrer;
    }

    public void setReferrer(String referrer) {
        this.referrer = referrer;
    }

    public int getDistinctResourcesCount() {
        return distinctResourcesCount;
    }

    public void setDistinctResourcesCount(int distinctResourcesCount) {
        this.distinctResourcesCount = distinctResourcesCount;
    }
}
