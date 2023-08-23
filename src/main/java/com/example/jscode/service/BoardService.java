package com.example.jscode.service;

import com.example.jscode.dto.BoardRequestDTO;
import com.example.jscode.dto.BoardResponseDTO;
import com.example.jscode.repository.BoardRepository;
import com.example.jscode.entity.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    @Transactional
    public Board getBoard(long id){
        return boardRepository.findById(id).orElseThrow(()-> new EntityNotFoundException());
    }
    @Transactional
    public Board createBoard(BoardRequestDTO boardRequestDTO) {
        Board board = boardRepository.save(boardRequestDTO.toEntity());
        return board;
    }
    @Transactional
    public List<Board>  getBoardList() {
        return  boardRepository.findAll(Sort.by(Sort.Direction.DESC,"id"))
                .stream()
                .limit(100)
                .collect(Collectors.toList());
    }
    @Transactional
    public Board modifyBoard(Long id,BoardRequestDTO boardRequestDTO){
        Board board = boardRepository.findById(id).orElseThrow(()-> new EntityNotFoundException());
        board.modify(boardRequestDTO.getBoardTitle(), boardRequestDTO.getBoardContents());
        boardRepository.save(board);
        return board;
    }
    @Transactional
    public void deleteBoard(Long id){
            boardRepository.delete(boardRepository.findById(id).orElseThrow(()-> new EntityNotFoundException()));
    }
    @Transactional
    public List<Board> searchBoard(String boardTitle){
             return boardRepository.findByBoardTitleContains(boardTitle)
                    .stream()
                    .sorted(Comparator.comparingLong(Board::getId).reversed())
                    .limit(100)
                    .collect(Collectors.toList());
        }
    }
