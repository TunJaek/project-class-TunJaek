package com.example.jscode.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table (name ="board_tb")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="boardTitle",nullable = false)
    private String boardTitle;

    @Column(name="boardContents",nullable = false)
    private String boardContents;

    @CreatedDate
    @Column(name="createdTime")
    private LocalDateTime createdTime;

    @Builder
    public Board(String boardTitle, String boardContents, LocalDateTime createdTime) {
        this.boardTitle = boardTitle;
        this.boardContents = boardContents;
        this.createdTime = LocalDateTime.now();
    }

    public void modify(String boardTitle, String boardContents) {
        this.boardTitle = boardTitle;
        this.boardContents = boardContents;
    }
}
