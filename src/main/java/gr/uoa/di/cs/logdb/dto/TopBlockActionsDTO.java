package gr.uoa.di.cs.logdb.dto;

import java.util.Date;

public class TopBlockActionsDTO {
    private String blockId;
    private Date logDate;
    private Long totalActions;

    public TopBlockActionsDTO(String blockId, Date logDate, Long totalActions) {
        this.blockId = blockId;
        this.logDate = logDate;
        this.totalActions = totalActions;
    }

    // Getters and setters
    public String getBlockId() {
        return blockId;
    }

    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }

    public Date getLogDate() {
        return logDate;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    public Long getTotalActions() {
        return totalActions;
    }

    public void setTotalActions(Long totalActions) {
        this.totalActions = totalActions;
    }
}
