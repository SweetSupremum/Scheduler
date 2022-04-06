package com.Scheduled.Scheduled_server.model;

public enum Role {
    USER, ADMIN;

    public String getAuthority() {
        return "ROLE_" + name();
    }
}
