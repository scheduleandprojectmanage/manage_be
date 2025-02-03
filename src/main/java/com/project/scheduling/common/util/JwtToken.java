package com.project.scheduling.common.util;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JwtToken {
    private String grantType; //JWT의 인증타입
    private String accessToken;
    private String refreshToken;
}
