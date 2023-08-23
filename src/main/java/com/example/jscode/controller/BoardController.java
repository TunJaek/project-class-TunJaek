package com.example.jscode.controller;

import com.example.jscode.dto.BoardRequestDTO;
import com.example.jscode.dto.BoardResponseDTO;
import com.example.jscode.entity.Board;
import com.example.jscode.response.ListResponse;
import com.example.jscode.response.ResponseService;
import com.example.jscode.response.SingleResponse;
import com.example.jscode.service.BoardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Api(tags ="게시판 만들기")
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;
    private final ResponseService responseService;

    @PostMapping("")
    @ApiOperation(value = "게시글 작성 기능", notes = "게시글을 작성하는 API")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses({
            @ApiResponse(code = 201, message = "게시글 작성 성공."),
            @ApiResponse(code = 400, message = "제목 또는 내용이 유효성 검사에서 실패할 때.", response = String.class)
    })
    public SingleResponse<BoardResponseDTO> createBoard(@RequestBody @Valid BoardRequestDTO boardRequestDTO) {
        BoardResponseDTO boardResponseDTO = BoardResponseDTO.from(boardService.createBoard(boardRequestDTO));
        return responseService.singleResponse(boardResponseDTO);
    }
    @GetMapping("") //게시글 목록 조회
    @ApiOperation(value = "게시글 전체 조회 기능", notes = "전체 게시글을 조회하는 API")
    @ApiResponses({
            @ApiResponse(code = 200, message = "게시글 전체 조회성공"),
            @ApiResponse(code = 404, message = "존재하지 않는 게시글입니다."),
            @ApiResponse(code = 500, message = "서버 내부 오류입니다, 관리자에게 문의 부탁드립니다.")
    })
    public ListResponse<Board> getBoardList(){
        return responseService.listResponse(boardService.getBoardList());
    }

    @ApiOperation(value = "특정 게시글 조회 기능", notes = "특정 게시글만 조회하는 API")
    @ApiResponses({
            @ApiResponse(code = 200, message = "특정 게시글 조회 성공"),
            @ApiResponse(code = 404, message = "존재하지 않는 게시글입니다."),
            @ApiResponse(code = 500, message = "서버 오류입니다 관리자에게 문의 부탁드립니다.")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping("/{id}") //특정 게시판 조회
    public SingleResponse<Board> getBoard(@PathVariable("id") Long id){
        return responseService.singleResponse(boardService.getBoard(id));
    }
    @DeleteMapping("/{id}") //게시판 삭제
    @ApiOperation(value = "특정 게시글 삭제", notes = "특정 id의 게시글을 삭제하는 API")
    @ApiResponses({
            @ApiResponse(code = 204, message = "게시글 삭제 성공"),
            @ApiResponse(code = 404, message = "존재하지 않는 게시글입니다."),
            @ApiResponse(code = 500, message = "서버 내부 오류입니다. 관리자에게 문의해주시기 바랍니다.")
    })
    public ResponseEntity<?> deleteBoard(@PathVariable("id") Long id){
         boardService.deleteBoard(id);
        return ResponseEntity.ok().body("삭제가 완료되었습니다.");
    }

    @PutMapping("/{id}") //게시판 수정
    @ApiOperation(value = "특정 게시글 수정", notes = "특정 id의 게시글을 수정하는 API")
    @ApiResponses({
            @ApiResponse(code = 200, message = "게시글 수정 성공"),
            @ApiResponse(code = 404, message = "존재하지 않는 게시글입니다."),
            @ApiResponse(code = 500, message = "서버 내부 오류입니다. 관리자에게 문의해주시기 바랍니다.")
    })
    public SingleResponse<Board> modifyBoard(@PathVariable("id") Long id, @RequestBody @Valid BoardRequestDTO boardRequestDTO){
        return responseService.singleResponse(boardService.modifyBoard(id, boardRequestDTO));
    }
    @GetMapping("/search") //게시판 검색
    @ApiOperation(value = "게시글 검색",notes = "검색 조건에 맞는 게시글을 조회하는 API")
    @ApiResponses({
            @ApiResponse(code = 200, message = "게시글 검색성공"),
            @ApiResponse(code = 400, message = "검색 키워드는 공백을 제외한 1글자 이상이어야 합니다."),
            @ApiResponse(code = 404, message = "존재하지 않는 게시글입니다."),
            @ApiResponse(code = 500, message = "서버 내부 오류입니다. 관리자에게 문의해주시기 바랍니다.")
    })
    public ListResponse<Board> searchBoard(@RequestParam(value ="boardTitle") @Valid String boardTitle){
        return responseService.listResponse(boardService.searchBoard(boardTitle)) ;
    }
}