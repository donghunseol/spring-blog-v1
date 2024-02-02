package shop.mtcoding.blog.board;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

public class BoardResponse {

    // bt.id, bt.content, bt.title, bt.created_at, ut.id uid, ut.username
    @AllArgsConstructor
    @Data
    public static class DetailDTO {
        private Integer id;
        private String title;
        private String content;
        private Timestamp createdAt; // 표준
        private Integer userId;
        private String username;
    }
}

