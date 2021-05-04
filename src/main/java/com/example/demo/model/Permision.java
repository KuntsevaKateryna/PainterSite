package com.example.demo.model;

/**
 * permissions for different roles
 */
public enum Permision {
    WRITING("write"),
    READING("read");

    private final String permission;

    Permision(String permission) {
        this.permission = permission;
    }
    public String getPermission() {
        return permission;
    }




}
