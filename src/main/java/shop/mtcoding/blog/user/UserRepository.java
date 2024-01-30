package shop.mtcoding.blog.user;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

@Repository // IoC에 뜬다. 내가 new를 안해도 된다.
public class UserRepository {

    private EntityManager em;

    public UserRepository(EntityManager em) {
        this.em = em;
    }

    @Transactional
    public void save(UserRequest.joinDTO requestDTO){ // Query를 직접 짬
        Query query = em.createNativeQuery("insert into user_tb (username, password, email) values (?, ?, ?)");
        query.setParameter(1, requestDTO.getUsername());
        query.setParameter(2, requestDTO.getPassword());
        query.setParameter(3, requestDTO.getEmail());
        
        query.executeUpdate(); // DB로 전송
    }

    @Transactional
    public void saveV2(UserRequest.joinDTO requestDTO){ // 통신을 통해서 받은 데이터를 Entity라는 클래스로 옮김 (하이버 네이트)
        User user = new User();
        user.setUsername(requestDTO.getUsername());
        user.setPassword(requestDTO.getPassword());
        user.setEmail(requestDTO.getEmail());

        em.persist(user);
    }

    public User findByUsernameAndPassword(UserRequest.loginDTO requestDTO) {
        Query query = em.createNativeQuery("select * from user_tb where username=? and password=?", User.class); // 타입만 적어주면 자동으로 파싱해줌
        query.setParameter(1, requestDTO.getUsername());
        query.setParameter(2, requestDTO.getPassword());

        User user = (User) query.getSingleResult();
        return user;
    }
}
