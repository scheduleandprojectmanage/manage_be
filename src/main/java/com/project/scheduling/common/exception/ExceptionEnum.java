package com.project.scheduling.common.exception;

import org.springframework.http.HttpStatus;

public enum ExceptionEnum {
    // 토큰 관련 예외
    INVALID_TOKEN("유효하지 않은 토큰입니다", HttpStatus.UNAUTHORIZED),
    TIMEOUT_TOKEN("만료된 토큰입니다", HttpStatus.UNAUTHORIZED),
    BLACKLISTED_TOKEN("토큰이 블랙리스트에 등록되어 있습니다", HttpStatus.UNAUTHORIZED),
    UNSUPPORTED_TOKEN("지원하지 않는 토큰 형식입니다", HttpStatus.UNAUTHORIZED),
    TOKEN_MISMATCH("토큰 정보가 일치하지 않습니다", HttpStatus.UNAUTHORIZED),
    TOKEN_NOT_FOUND("토큰이 존재하지 않습니다", HttpStatus.UNAUTHORIZED),

    // 회원 관련 예외
    MEMBER_NOT_FOUND("존재하지 않는 회원입니다", HttpStatus.NOT_FOUND),
    DUPLICATE_EMAIL("이미 존재하는 이메일입니다", HttpStatus.CONFLICT),
    INVALID_PASSWORD("비밀번호가 일치하지 않습니다", HttpStatus.BAD_REQUEST),
    OAUTH_PROCESSING_ERROR("OAuth 처리 중 오류가 발생했습니다", HttpStatus.BAD_REQUEST),

    // 리소스 관련 예외
    RESOURCE_NOT_FOUND("요청한 리소스를 찾을 수 없습니다", HttpStatus.NOT_FOUND),

    // 권한 관련 예외
    ACCESS_DENIED("접근이 거부되었습니다", HttpStatus.FORBIDDEN),

    // 서버 예외
    SERVER_ERROR("서버 에러가 발생했습니다", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String message;
    private final HttpStatus status;

    ExceptionEnum(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }
}