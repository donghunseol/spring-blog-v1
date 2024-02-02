package shop.mtcoding.blog.board;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Controller
public class BoardController {

    private final HttpSession session;
    private final BoardRepository boardRepository;

    // 데이터 리턴시 (RestController or ResponseBody) - 객체를 리턴하면 스프링이 json으로 변환
    @GetMapping("/api/board/{id}")
    public @ResponseBody Board apiBoard(@PathVariable int id) { // @ResponseBody -> 파일을 안 찾고 데이터를 리턴한다.
        return boardRepository.findById(id);
    }

    @GetMapping({"/", "/board"})
    public String index() {
        return "index";
    }

    @GetMapping("/board/saveForm")
    public String saveForm() {
        return "board/saveForm";
    }

    @GetMapping("/board/1")
    public String detail() {
        return "board/detail";
    }
}
