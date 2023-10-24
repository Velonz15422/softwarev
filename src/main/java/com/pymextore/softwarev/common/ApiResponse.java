package com.pymextore.softwarev.common;

import java.time.LocalDateTime;

public class ApiResponse {
    private final boolean success;
    private final String message;

    public ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public String getMessage() {
        return this.message;
    }
    
    public String getTime(){
        return LocalDateTime.now().toString();
    }
}
