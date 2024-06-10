package com.example.spring_todo.global.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // Global HttpStatus
    BAD_REQUEST(400, "Invalid Request"),
    UNAUTHORIZED_REQUEST(401, "Unauthorized"),
    FORBIDDEN_ACCESS(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    METHOD_NOT_ALLOWED(405, "Not Allowed method"),
    INTERNAL_SERVER_ERROR(500, "Server Error"),

    // Todo
    NOT_FOUND_TODO(404, "TODO를 찾을 수 없습니다."),
    UNAUTHORIZED_UPDATE_TODO(401, "TODO를 업데이트할 권한이 없습니다."),
    ALREADY_LIKED(403, "이미 좋아요를 누른 TODO입니다"),
    NOT_FOUND_TODO_LIKE(404, "해당 TODO의 좋아요 기록을 찾을 수 없습니다."),

    // User
    NOT_FOUND_USER(404, "현재 로그인한 사용자를 찾을 수 없습니다."),
    PASSWORD_MISMATCH(401, "비밀번호가 일치하지 않습니다.");

    private final int status;
    private final String message;

    ErrorCode(final int status, final String message) {
        this.status = status;
        this.message = message;
    }
}
