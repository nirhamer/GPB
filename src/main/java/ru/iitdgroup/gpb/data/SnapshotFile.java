package ru.iitdgroup.gpb.data;

import java.util.ArrayList;
import java.util.List;

public class SnapshotFile {
    private List<ScannedFile> files = new ArrayList<>();
    private String sha256Checksum;

    public List<ScannedFile> getFiles() {
        return files;
    }

    public void setFiles(List<ScannedFile> files) {
        this.files = files;
    }

    public String getSha256Checksum() {
        return sha256Checksum;
    }

    public void setSha256Checksum(String sha256Checksum) {
        this.sha256Checksum = sha256Checksum;
    }
}
