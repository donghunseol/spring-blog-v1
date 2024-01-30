package shop.mtcoding.blog.user;

import lombok.Data;

/***
 * 요청 DTO = Data Transfer Object
 */
public class UserRequest {

    @Data
    public static class joinDTO{
        private String username;
        private String password;
        private String email;
    }

    @Data
    public static class loginDTO{
        private String username;
        private String password;
    }
}
