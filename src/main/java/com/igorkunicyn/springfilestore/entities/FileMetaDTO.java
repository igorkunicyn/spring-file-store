package com.igorkunicyn.springfilestore.entities;

import java.util.UUID;

public class FileMetaDTO {
    private UUID hash;

    private String fileName;

    private long size;

    private String data;

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public FileMetaDTO(String fileName) {
        this.fileName = fileName;
    }

    public UUID getHash() {
        return hash;
    }

    public void setHash(UUID hash) {
        this.hash = hash;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
