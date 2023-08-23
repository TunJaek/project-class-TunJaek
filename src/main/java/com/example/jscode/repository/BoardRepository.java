package com.example.jscode.repository;

import com.example.jscode.dto.BoardRequestDTO;
import com.example.jscode.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board,Long> {
    // 게시글 작성및 수정
    Board save(Board board);

    // 모든 게시글 조회
    List<Board> findAll();

    // 특정 게시글 삭제
    void deleteById(Long id);

    // 검색 키워드로 조회
    List<Board> findByBoardTitleContains(String boardTitle);


}
