package com.example.jscode.service;

import com.example.jscode.dto.MemberRequestDTO;
import com.example.jscode.entity.Member;
import com.example.jscode.entity.Role;
import com.example.jscode.exception.EmailNotRegistrationException;
import com.example.jscode.exception.MemberEmailAlreadyExistsException;
import com.example.jscode.exception.PasswordNotMatchException;
import com.example.jscode.repository.MemberRepository;
import com.example.jscode.security.JsonWebTokenService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JsonWebTokenService jsonWebTokenService;




    @Transactional
    public Member join(MemberRequestDTO memberRequestDTO){
        if(!validateDuplicated(memberRequestDTO.getEmail())){
            Member member = memberRepository.save(Member.builder()
                    .password(passwordEncoder.encode(memberRequestDTO.getPassword()))
                    .email(memberRequestDTO.getEmail())
                    .joinTime(memberRequestDTO.getJoinTime())
                    .role(Role.ROLE_MEMBER).build());
            return member;
        } throw new MemberEmailAlreadyExistsException();
    }
    @Transactional
    public String login(MemberRequestDTO memberRequestDTO){
        Member member = memberRepository.findByEmail(memberRequestDTO.getEmail())
                .orElseThrow(() -> new EmailNotRegistrationException("등록되지 않은 이메일 입니다."));
        if(!passwordEncoder.matches(memberRequestDTO.getPassword(),member.getPassword())) {
            throw new PasswordNotMatchException("비밀번호가 일치하지 않습니다.");
        }
        Long id= member.getId();
        return jsonWebTokenService.tokenIssuance(memberRequestDTO,id);

    }
    @Transactional
    public Member user(HttpServletRequest request){
        return  jsonWebTokenService.getUser(jsonWebTokenService.resolveToken(request));
    }
    @Transactional
    private boolean validateDuplicated(String email) {
        if (memberRepository.findByEmail(email).isPresent()) {
            throw new MemberEmailAlreadyExistsException();
        }
        return false;
    }
}
