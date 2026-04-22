package com.pqrs.system_pqrs.dto;

public class FileUploadDTO {
    private String fileName;
    private String filePath;
    private Long peticionId;

    public FileUploadDTO() {}

    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }

    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }

    public Long getPeticionId() { return peticionId; }
    public void setPeticionId(Long peticionId) { this.peticionId = peticionId; }
}