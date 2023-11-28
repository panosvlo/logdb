package gr.uoa.di.cs.logdb.dto;

import org.springframework.web.multipart.MultipartFile;

public class LogFileUploadDTO {
    private MultipartFile file;
    private Integer logTypeId;

    // Getters and Setters
    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public Integer getLogTypeId() {
        return logTypeId;
    }

    public void setLogTypeId(Integer logTypeId) {
        this.logTypeId = logTypeId;
    }
}
