package com.project.scheduling.user.oauth;

import java.util.Map;

public interface OAuth2UserInfo {
    Map<String, Object> getAttributes();
    String getOAuth2Id();
    String getEmail();
    String getUserIdentifier();

}