package com.project.scheduling.user.service;

import com.project.scheduling.common.util.JwtToken;
import com.project.scheduling.user.dto.LogInRequestDto;
import com.project.scheduling.user.dto.UserSignUpRequestDto;
import com.project.scheduling.user.entity.User;
import com.project.scheduling.user.repository.UserRepository;
import com.project.scheduling.common.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenProvider jwtTokenProvider;


    /** 회원가입 */
    public void save(UserSignUpRequestDto userSignUpRequestDto) {
        String userEmail = userSignUpRequestDto.getEmail();
        String userPassword = bCryptPasswordEncoder.encode(userSignUpRequestDto.getPassword());

        Boolean isExist = userRepository.existsByEmail(userEmail);

        if (isExist) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }

        User user = User.createUser(
                userSignUpRequestDto.getName(),
                userSignUpRequestDto.getEmail(),
                userPassword
        );

        User savedUser = userRepository.save(user);
    }


    /** 로그인 */
    public JwtToken login(LogInRequestDto request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 이메일입니다"));

        if (!bCryptPasswordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 틀렸습니다");
        }

        return JwtToken.builder()
                .grantType("Local")
                .accessToken(jwtTokenProvider.generateAccessToken(user.getEmail()))
                .refreshToken(jwtTokenProvider.generateRefreshToken(user.getEmail()))
                .build();
    }

}
