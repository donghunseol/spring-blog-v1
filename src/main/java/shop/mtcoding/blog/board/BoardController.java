package shop.mtcoding.blog.board;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import shop.mtcoding.blog._core.PagingUtil;
import shop.mtcoding.blog.user.User;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BoardController {

    private final BoardRepository boardRepository;
    // IoC 컨테이너에 세션에 접근할 수 있는 변수가 들어가 있음. DI(Dependency Injection, 의존 관계 주입) 하면 됨
    private final HttpSession session;

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

    @GetMapping("/board/{id}")
    public String detail(@PathVariable int id, HttpServletRequest request) {

        BoardResponse.DetailDTO responseDTO = boardRepository.findById(id);
        request.setAttribute("board", responseDTO);

        // 1 ~ 3. 권한 체크 -> 권한 실패 시 403을 터트림 (다른건 인증체크도 존재한다)
        // 1. 해당 페이지의 주인 여부
        boolean owner = false;

        // 2. 작성자 userId 확인하기
        int boardUserId = responseDTO.getUserId();

        // 3. 로그인 여부 체크
        User sessionUserId = (User) session.getAttribute("sessionUser");

        if (sessionUserId != null) { // null이 아니면 로그인 된 것
            if (boardUserId == sessionUserId.getId()) { // 작성자와 로그인된 아이디가 동일한지 확인
                owner = true;
            }
        }

        request.setAttribute("owner", owner);

        return "board/detail";
    }
}
