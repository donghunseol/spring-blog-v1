package shop.mtcoding.blog.board;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import shop.mtcoding.blog.user.User;

import java.sql.Timestamp;

@Data // SET, GET, ToString
@Entity // 깃발을 꼽는 으로 이해하면될듯
@Table(name = "board_tb") // 테이블 이름 설정
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String content;

    @ManyToOne // Many 는 Board One은 User (1대 N) -> 자동으로 Foring키로 만들어 준다.
    private User user;

    @CreationTimestamp
    private Timestamp createdAt;
}
