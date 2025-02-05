package com.project.scheduling.user.oauth;

import java.util.Map;

public class NaverOAuth2UserInfo implements OAuth2UserInfo {
    private Map<String, Object> attributes;

    public NaverOAuth2UserInfo(Map<String, Object> attributes) {
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
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        return (String) response.get("email");
    }

    @Override
    public String getUserIdentifier() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        return (String) response.get("id");
    }
}