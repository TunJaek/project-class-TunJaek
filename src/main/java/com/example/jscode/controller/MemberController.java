package com.example.jscode.controller;

import com.example.jscode.dto.MemberRequestDTO;
import com.example.jscode.dto.MemberResponseDTO;
import com.example.jscode.response.ListResponse;
import com.example.jscode.response.ResponseService;
import com.example.jscode.response.SingleResponse;
import com.example.jscode.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Api(tags ="회원 관리")
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final ResponseService responseService;

    @PostMapping("/join")
    @ApiOperation(value = "회원가입 기능", notes = "회원가입하는 API")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses({
            @ApiResponse(code = 201, message = "회원가입 성공."),
            @ApiResponse(code = 400, message = "회원가입 실패", response = String.class)

    })
    public SingleResponse<MemberResponseDTO> join(@RequestBody @Valid MemberRequestDTO memberRequestDTO){
        MemberResponseDTO memberResponseDTO = MemberResponseDTO.from(memberService.join(memberRequestDTO));
        return  responseService.singleResponse(memberResponseDTO);
    }

    @PostMapping("/login")
    public String login(@RequestBody @Valid MemberRequestDTO memberRequestDTO){
        return memberService.login(memberRequestDTO);
    }

    @GetMapping("") //유저 조회
    public SingleResponse<MemberResponseDTO> user(HttpServletRequest request){
        MemberResponseDTO memberResponseDTO =MemberResponseDTO.from(memberService.user(request));
        return  responseService.singleResponse(memberResponseDTO);
    }
}
