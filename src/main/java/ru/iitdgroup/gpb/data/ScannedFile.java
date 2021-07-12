package ru.iitdgroup.gpb.data;

public class ScannedFile {
    private final String relativePathAndName;
    private final String sha256;
    private boolean isProcessed = false;

    public ScannedFile(String relativePathAndName, String sha256) {
        this.relativePathAndName = relativePathAndName;
        this.sha256 = sha256;
    }

    public String getRelativePathAndName() {
        return relativePathAndName;
    }

    public String getSha256() {
        return sha256;
    }

    public boolean isProcessed() {
        return isProcessed;
    }

    public void setProcessed(boolean processed) {
        isProcessed = processed;
    }
}
