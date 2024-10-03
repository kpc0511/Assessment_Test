package com.maybank.platform.services.util.enums;

public enum FileType {
    TXT("TXT"),
    CSV("CSV"),
    ;
    private final String key;

    FileType(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
