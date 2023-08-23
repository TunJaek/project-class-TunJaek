package com.example.jscode.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CommonResponse {
    protected boolean success;
    protected int code;
    protected String message;

    public CommonResponse() {

    }

    @Builder
    public CommonResponse(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public static CommonResponse of (boolean success, int code, String message ) {
        return new CommonResponse(success,code,message);
    }

}
