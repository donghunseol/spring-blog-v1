package shop.mtcoding.blog.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
@Controller
public class UserController {

    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/join")
    public String join(UserRequest.joinDTO requestDTO){
        System.out.println(requestDTO);

        // 1. 유효성 검사
        if(requestDTO.getUsername().length() < 3){
            return "error/400";
        }

        // 2. Model 에게 위임하기
        userRepository.save(requestDTO);

        // DB INSERT

        return "redirect:/loginForm";
    }
    @PostMapping("/login")
    public String login(UserRequest.loginDTO requestDTO){
        // 1. 유효성 검사
        if(requestDTO.getUsername().length() < 3){
            return "error/400";
        }

        // 2. 모델 연결 select * from user_tb where username=? and password=?
        User user = userRepository.findByUsernameAndPassword(requestDTO);

        System.out.println(user);

        // 3. 응답
        return "redirect:/";
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
        return "redirect:/";
    }
}
