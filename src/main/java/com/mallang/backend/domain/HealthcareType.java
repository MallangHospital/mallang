package com.mallang.backend.domain;

public enum HealthcareType {
    단체_건강검진("단체 건강검진"),
    개인_건강검진("개인 건강검진"),
    기업_건강검진("기업 건강검진");

    private final String displayName;

    HealthcareType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}