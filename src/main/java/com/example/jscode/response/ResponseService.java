package com.example.jscode.response;


import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponseService {

       public<T> SingleResponse<T> singleResponse(T data) {
        SingleResponse singleResponse = new SingleResponse();
        singleResponse.data = data;
        successResponse(singleResponse);
        return singleResponse;
       }

       public<T> ListResponse<T> listResponse(List<T> dataList){
        ListResponse listResponse = new ListResponse();
        listResponse.dataList=dataList;
        successResponse(listResponse);
        return listResponse;
        }
    public CommonResponse getErrorResponse(int code, String message) {
        return CommonResponse.of(false,code,message);
    }

        void successResponse(CommonResponse response){
        response.code=0;
        response.success=true;
        response.message="SUCCESS";
        }
    }

