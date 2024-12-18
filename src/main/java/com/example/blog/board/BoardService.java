package com.example.blog.board;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {
    // 책임: 비즈니스 로직

    private final BoardRepository boardRepository;

    public List<BoardResponse.ReadDTO> 게시글목록보기() {
        List<BoardResponse.ReadDTO> readDtos = new ArrayList<>();

        List<Board> boardList = boardRepository.findAll();

        // 딥 카피
        for (Board board : boardList) {
            BoardResponse.ReadDTO readDto = new BoardResponse.ReadDTO(board);
            readDtos.add(readDto);
        }
        return readDtos;
    }

    // 게시글상세보기랑 같은 내용이지만 따로 만드는게 좋다.
    public BoardResponse.UpdateFormDTO 게시글수정화면보기(int id) {
        Board board = boardRepository.findById(id);
        return new BoardResponse.UpdateFormDTO(board);
    }

    public BoardResponse.DetailDTO 게시글상세보기(int id) {
        Board board = boardRepository.findById(id);
        return new BoardResponse.DetailDTO(board);
    }

    // 이게 있어야 commit을 한다. 메서드 실행 도중 문제가 생기면 롤백
    @Transactional
    public void 게시글쓰기(BoardRequest.SaveDTO saveDTO) {
        boardRepository.save(saveDTO.getTitle(), saveDTO.getContent());
    }

    @Transactional
    public void 게시글삭제(int id) {
        boardRepository.delete(id);
    } // commit or rollback (select빼고 delete, insert, update에 필요)

    @Transactional
    public void 게시글수정하기(int id, BoardRequest.UpdateDTO updateDTO) {
        boardRepository.update(id, updateDTO.getTitle(), updateDTO.getContent());
    }
}
