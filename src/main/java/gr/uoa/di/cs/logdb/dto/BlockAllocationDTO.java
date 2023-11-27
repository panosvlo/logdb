package gr.uoa.di.cs.logdb.dto;

import java.time.LocalDateTime;

public class BlockAllocationDTO {
    private String blockId;
    private LocalDateTime allocatedTime;
    private LocalDateTime replicatedTime;

    public BlockAllocationDTO(String blockId, LocalDateTime allocatedTime, LocalDateTime replicatedTime) {
        this.blockId = blockId;
        this.allocatedTime = allocatedTime;
        this.replicatedTime = replicatedTime;
    }

    // Getters
    public String getBlockId() {
        return blockId;
    }

    public LocalDateTime getAllocatedTime() {
        return allocatedTime;
    }

    public LocalDateTime getReplicatedTime() {
        return replicatedTime;
    }

    // Setters
    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }

    public void setAllocatedTime(LocalDateTime allocatedTime) {
        this.allocatedTime = allocatedTime;
    }

    public void setReplicatedTime(LocalDateTime replicatedTime) {
        this.replicatedTime = replicatedTime;
    }
}

