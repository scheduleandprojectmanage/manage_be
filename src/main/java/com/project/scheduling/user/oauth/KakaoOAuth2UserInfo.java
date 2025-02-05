package com.project.scheduling.user.oauth;

import java.util.Map;

public class KakaoOAuth2UserInfo implements OAuth2UserInfo {
    private Map<String, Object> attributes;

    public KakaoOAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getOAuth2Id() {
        return getUserIdentifier();
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getUserIdentifier() {
        return String.valueOf(attributes.get("id")); // 카카오는 "id"로 식별자 제공
    }
}
