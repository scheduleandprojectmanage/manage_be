package com.project.scheduling.user.controller;

import com.project.scheduling.common.util.JwtToken;
import com.project.scheduling.user.dto.LogInRequestDto;
import com.project.scheduling.user.dto.UserSignUpRequestDto;
import com.project.scheduling.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController //모든 메서드의 반환값을 JSON/XML로 반환
@RequestMapping("/user")
@RequiredArgsConstructor //final이나 @NonNull 필드에 대한 생성자를 자동으로 생성
@Validated //메서드의 파라미터나 반환값에 대한 유효성 검증을 활성화
//@Tag (name="User API", description =  "사용자 관련 API")
@Slf4j
public class UserController {

    private final UserService userService;

    /**
     * 회원가입
     */
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserSignUpRequestDto userSignUpRequestDto) {
        userService.save(userSignUpRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입에 성공했습니다.");
    }

    /**
     * 로그인
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LogInRequestDto request) {
        JwtToken tokens = userService.login(request);
        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + tokens.getAccessToken())
                .header("Refresh-Token", "Bearer " + tokens.getRefreshToken())
                .body(tokens);
    }
}
