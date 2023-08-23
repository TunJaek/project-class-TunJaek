package com.example.jscode.dto;

import com.example.jscode.entity.Board;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardResponseDTO {

        private Long id;
        private String boardTitle;
        private String boardContents;
        @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm")
        private LocalDateTime createTime;

        private BoardResponseDTO(Long id, String boardTitle, String boardContents,LocalDateTime createTime){
            this.id= id;
            this.boardTitle= boardTitle;
            this.boardContents= boardContents;
            this.createTime=createTime;
        }

        public static BoardResponseDTO from(Board board){
            return new BoardResponseDTO(board.getId(), board.getBoardTitle(), board.getBoardContents(),board.getCreatedTime());
        }
    }
