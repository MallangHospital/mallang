package com.mallang.backend.domain;

public enum Role {
    ROLE_ADMIN("admin"), ROLE_MEMBER("member");

    private String value;

    Role(String value) {
        this.value = value;
    }
}
