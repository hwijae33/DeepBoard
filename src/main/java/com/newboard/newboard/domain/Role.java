package com.newboard.newboard.domain;

import lombok.Getter;

@Getter
public enum Role {
    USER("ROLE_USER"),ADMIN("ROLE_ADMIN");

    Role(String roleUser) {
        this.roleUser = roleUser;
    }
    private  String roleUser;
}
