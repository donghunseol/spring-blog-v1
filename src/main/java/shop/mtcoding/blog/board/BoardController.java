package shop.mtcoding.blog.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.mtcoding.blog._core.PagingUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class BoardController {

    private final HttpSession session;
    private final BoardRepository boardRepository;

    // http://localhost:8080?page=0
    @GetMapping({"/", "/board"})
    public String index(HttpServletRequest request, @RequestParam(defaultValue = "0") int page) {
        System.out.println("페이지 : " + page);
        List<Board> boardList = boardRepository.findAll(page);
        request.setAttribute("boardList", boardList); // 가방(리퀘스트)에 담는 것 MVC 패턴

        int currentPage = page; // 현재 페이지
        int nextPage = currentPage + 1;
        int prevPage = currentPage - 1; // 뷰에 자바 코드가 최소화 된다.
        request.setAttribute("nextPage", nextPage);
        request.setAttribute("prevPage", prevPage); // 가져와서 넣기!

        boolean first = PagingUtil.isFirst(currentPage);
        boolean last = PagingUtil.isLast(currentPage, 4);


        // t = 4, c = 0, last = false
        // t = 4, c = 1, last = true

        request.setAttribute("first", first);
        request.setAttribute("last", last);

        // disabled 도 편하게 수정하기 // {{#first}}disabled{{/first}} 이걸 {{disabled}}만 넣어서 해결 가능
        String disabled = currentPage == 0 ? "disabled" : "";
        request.setAttribute("disabled", disabled);

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
