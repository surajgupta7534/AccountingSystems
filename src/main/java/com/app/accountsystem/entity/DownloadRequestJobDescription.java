package com.app.accountsystem.entity;

public class DownloadRequestJobDescription {
    private String type = "download";
    private Credentials credentials;
    private String fileName;
    private String fileSystem;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileSystem() {
        return fileSystem;
    }

    public void setFileSystem(String fileSystem) {
        this.fileSystem = fileSystem;
    }
}