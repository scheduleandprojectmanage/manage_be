package com.project.scheduling.user.oauth;


import java.util.Map;

public class GoogleOAuth2UserInfo implements OAuth2UserInfo {
    private Map<String, Object> attributes;

    public GoogleOAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getOAuth2Id() {
        return getUserIdentifier();  // 동일한 값을 반환하므로 중복 제거
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getUserIdentifier() {
        return (String) attributes.get("sub");
    }
}