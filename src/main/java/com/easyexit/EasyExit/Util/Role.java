package com.easyexit.EasyExit.Util;

public enum Role {
    ADMIN("Admin"),
    STUDENT("Student"),
    GUARD("Guard");

    private final String label;

    Role(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
