package com.example.cellphoneweb.models;

public enum Role {
    ADMIN("ROLE_ADMIN"),USER("ROLE_USER");
    private String role;
    Role(String role){
        this.role = role;
    }

    public String getRole(){
        return this.role;
    }
}
