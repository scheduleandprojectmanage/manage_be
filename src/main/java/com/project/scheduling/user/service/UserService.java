package com.project.scheduling.user.service;

import com.project.scheduling.user.dto.UserSignUpRequestDto;
import com.project.scheduling.user.entity.User;
import com.project.scheduling.user.repository.UserRepository;
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
}
