package com.example.jscode.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name="member_tb")
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Builder
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "joinTime")
    @CreatedDate
    private LocalDateTime joinTime;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public Member(String password, String email, LocalDateTime joinTime){
        this.password=password;
        this.email=email;
        this.joinTime=joinTime.now();
    }
    public Member(Long id, String password, String email){
        this.id=id;
        this.password=password;
        this.email=email;
    }




}