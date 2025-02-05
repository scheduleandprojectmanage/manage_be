package com.project.scheduling.user.oauth;

import com.project.scheduling.user.entity.ProviderInfo;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;

import java.util.Map;

import static com.project.scheduling.user.entity.ProviderInfo.*;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(ProviderInfo providerInfo, Map<String, Object> attributes) {
        switch (providerInfo) {
            case KAKAO -> {
                return new KakaoOAuth2UserInfo(attributes);
            }
            case NAVER -> {
                return new NaverOAuth2UserInfo(attributes);
            }
            case GOOGLE -> {
                return new GoogleOAuth2UserInfo(attributes);
            }
        }
        throw new OAuth2AuthenticationException("INVALID PROVIDER TYPE");
    }
}