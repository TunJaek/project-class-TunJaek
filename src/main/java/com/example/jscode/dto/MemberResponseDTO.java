package com.example.jscode.dto;

import com.example.jscode.entity.Member;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemberResponseDTO {

    private Long id;
    private String email;
    private String password;

    public MemberResponseDTO(Long id, String email,String password){
        this.id=id;
        this.email=email;
        this.password=password;
    }
    public static MemberResponseDTO from(Member member){
        return  new MemberResponseDTO(member.getId(), member.getEmail(), member.getPassword());
    }

}
