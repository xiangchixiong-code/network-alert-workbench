package com.xianggon.netalerts.common;

import java.time.LocalDateTime;

public record ApiResponse<T>(boolean success, T data, String message, LocalDateTime timestamp) {
    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(true, data, "ok", LocalDateTime.now());
    }
}
