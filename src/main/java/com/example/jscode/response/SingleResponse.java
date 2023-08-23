package com.example.jscode.response;

import lombok.Getter;

@Getter
public class SingleResponse<T> extends CommonResponse{
    T data;
}
