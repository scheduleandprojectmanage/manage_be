package com.project.scheduling.user.entity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    ADMIN("ROLE_ADMIN", "관리자"),
    OWNER("ROLE_OWNER", "식당주인"),
    USER("ROLE_USER", "일반사용자");

    private final String key;
    private final String title;

}
