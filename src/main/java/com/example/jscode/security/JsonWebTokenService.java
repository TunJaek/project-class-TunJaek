package com.example.jscode.security;


import com.example.jscode.dto.MemberRequestDTO;
import com.example.jscode.entity.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;


import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JsonWebTokenService {
   private final SecurityUserDetailService userDetailsService;
   private String secretKey = "test";
   private long expiredDate = 30 * 60 * 1000L;
   private SecurityConfig securityConfig;
   public String tokenIssuance(MemberRequestDTO memberRequestDTO, Long id){
      return Jwts.builder()
              .setHeaderParam("typ","JWT")//토큰타입
              .setSubject("userToken")
              .setExpiration(new Date(System.currentTimeMillis()+expiredDate))//토큰유효시간
              .claim("password",memberRequestDTO.getPassword())
              .claim("email",memberRequestDTO.getEmail())
              .claim("id",id)
              .signWith(SignatureAlgorithm.HS256,secretKey)
              .compact();
   }

   // JWT 토큰에서 인증 정보 조회
   public Authentication getAuthentication(String token) {
      UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserName(token));
      return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
   }


   // 토큰에서 회원 정보 추출
   public String getUserName (String token) {
     Claims claims=Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
     String email = claims.get("email", String.class);
     return email;
   }
   public Member getUser (String token) {
      Claims claims=Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
     Long id = claims.get("id", Long.class);
     String password =claims.get("password", String.class);
      String email = claims.get("email", String.class);
      return new Member(id,password,email);
   }

   // Request의 Header에서 token 값을 가져옵니다. "TOKEN": "TOKEN 값"
   public String resolveToken(HttpServletRequest request) {
      return request.getHeader("TOKEN");
   }

   // 토큰의 유효성 + 만료일자 확인
   public boolean validateToken(String token) {
      try {
         Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
         return !claims.getBody().getExpiration().before(new Date());
      } catch (java.lang.Exception e) {
         return false;
      }
   }
}
