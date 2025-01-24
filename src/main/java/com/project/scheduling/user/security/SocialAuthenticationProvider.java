package com.project.scheduling.user.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SocialAuthenticationProvider implements AuthenticationProvider {
    //일반 비밀번호 로그인 시에는 스프링 시큐리티에서 제공해주는 DaoAuthenticationProvider를 이용하면 되지만
    //소셜로그인의 경우에는 적절한 authenticationProvider 구현체가 없어서 직접 구현체를 작성해줘야 함

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }




}
