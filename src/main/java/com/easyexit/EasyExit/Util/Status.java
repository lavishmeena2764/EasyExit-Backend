package com.easyexit.EasyExit.Util;

public enum Status {
    APPROVED("Approved"),
    REJECTED("Rejected"),
    PENDING("Pending"),
    VERIFIED("Verified");

    private final String label;

    Status(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
