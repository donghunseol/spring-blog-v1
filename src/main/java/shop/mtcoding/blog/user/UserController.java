package shop.mtcoding.blog.user;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/***
 * 컨트롤러
 * 1. 요청받기 (URL - URI 포함)
 * 2. http body는 어떻게? (DTO)
 * 3. 기본 mime 전략 : x-www-formurlencoded (username=ssar&password=1234, 스프링이 이걸 파싱)
 * 4. 유효성 검사하기 (body 데이터가 있다면, 데이터 값이 있다면)
 * 5. 클라이언트가 View만 원하는지? 혹은 DB 처리 후 View도 원하는지?
 * 6. View만 원하면 View를 응답하면 끝
 * 7. DB처리를 원하면 Model(DAO)에게 위임 후 View를 응답하면 끝
 */
@RequiredArgsConstructor // final 붙은 것만 생성자로 생성
@Controller
public class UserController {

    private final UserRepository userRepository;
    private final HttpSession session;

    @PostMapping("/join")
    public String join(UserRequest.joinDTO requestDTO) {
        System.out.println(requestDTO);

        // 1. 유효성 검사
        if (requestDTO.getUsername().length() < 3) {
            return "error/400";
        }

        // 2. 동일 username 체크 (나중에 하나의 트랜잭션으로 묶는게 좋다, 서비스 레이어)
        User user = userRepository.findByUsername(requestDTO.getUsername());
        if (user == null) {
            userRepository.save(requestDTO);
        } else {
            return "error/400";
        }


        // 3. Model 에게 위임하기
        try {
            userRepository.save(requestDTO);
        } catch (Exception e) {

        }

        // DB INSERT

        return "redirect:/loginForm";
    }

    @PostMapping("/login")
    public String login(UserRequest.loginDTO requestDTO) {
        // 1. 유효성 검사
        if (requestDTO.getUsername().length() < 3) {
            return "error/400";
        }

        // 2. 모델 연결 select * from user_tb where username=? and password=?
        User user = userRepository.findByUsernameAndPassword(requestDTO);

//        // 유저가 null이면, error 페이지로
//        // 유저가 null이 아니면 session 만들고 index 페이지로 이동
//        System.out.println(user);

        if (user == null) {
            return "error/401";
        } else {
            session.setAttribute("sessionUser", user); // 자료구조가 setAttribute 는 힙이다.
            return "redirect:/";
        }
    }

    @GetMapping("/joinForm")
    public String joinForm() {
        return "user/joinForm";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "user/loginForm";
    }

    @GetMapping("/user/updateForm")
    public String updateForm() {
        return "user/updateForm";
    }

    @GetMapping("/logout")
    public String logout() {
        session.invalidate(); // 세션에 있는 것들을 삭제 시킨다. (서랍을 통째로 비워 버린다.)
        return "redirect:/";
    }
}
