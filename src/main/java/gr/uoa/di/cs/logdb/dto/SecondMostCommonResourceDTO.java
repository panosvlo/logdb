package gr.uoa.di.cs.logdb.dto;

public class SecondMostCommonResourceDTO {

    private String resourceName;
    private Long count;

    public SecondMostCommonResourceDTO(String resourceName, Long count) {
        this.resourceName = resourceName;
        this.count = count;
    }

    // Getters and Setters
    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
