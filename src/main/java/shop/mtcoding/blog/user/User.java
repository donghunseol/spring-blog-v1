package shop.mtcoding.blog.user;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data // SET, GET, ToString
@Entity // 깃발을 꼽는 으로 이해하면될듯
@Table(name = "user_tb") // 테이블 이름 설정
public class User {
    @Id // primary key 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto_Increment
    private int id;

    @Column(unique = true) // unique 값으로 설정
    private String username;

    @Column(length = 60, nullable = false) // 60자를 넘을 수 없고 null이 될수 없다.
    private String password;
    private String email;

    @CreationTimestamp // insert 될때 자동으로 넣어줌
    private LocalDateTime createdAt;
}
