package gr.uoa.di.cs.logdb.dto;

import java.util.Date;

public class BlockAllocationReplicationDTO {
    private String blockId;
    private Date allocatedTime;
    private Date replicatedTime;

    public BlockAllocationReplicationDTO(String blockId, Date allocatedTime, Date replicatedTime) {
        this.blockId = blockId;
        this.allocatedTime = allocatedTime;
        this.replicatedTime = replicatedTime;
    }

    // Getters and Setters
    public String getBlockId() {
        return blockId;
    }

    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }

    public Date getAllocatedTime() {
        return allocatedTime;
    }

    public void setAllocatedTime(Date allocatedTime) {
        this.allocatedTime = allocatedTime;
    }

    public Date getReplicatedTime() {
        return replicatedTime;
    }

    public void setReplicatedTime(Date replicatedTime) {
        this.replicatedTime = replicatedTime;
    }
}
