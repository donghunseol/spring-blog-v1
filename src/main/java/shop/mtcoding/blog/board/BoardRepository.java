package shop.mtcoding.blog.board;

import lombok.RequiredArgsConstructor;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Repository;
import shop.mtcoding.blog._core.Constant;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class BoardRepository {
    private final EntityManager em;

    public List<Board> findAll(int page) {
        int value = page * Constant.PAGING_COUNT;
        Query query = em.createNativeQuery("select * from board_tb order by id desc limit ?, ?", Board.class);
        query.setParameter(1, value);
        query.setParameter(2, Constant.PAGING_COUNT);

        List<Board> boardList = query.getResultList(); // 여러건이니
        return boardList;
    }

    public int count() {
        Query query = em.createNativeQuery("select count(*) from board_tb");
        Number count = (Number) query.getSingleResult();
        return count.intValue();
    }

    public BoardResponse.DetailDTO findById(int id) {
        // Entity 가 아닌 것은 JPA가 파싱 안해준다. (Entity가 아닌 이유는? Join)
        Query query = em.createNativeQuery("select bt.id, bt.title, bt.content, bt.created_at, bt.user_id, ut.username from board_tb bt inner join user_tb ut on bt.user_id = ut.id where bt.id = ?");
        query.setParameter(1, id);

        JpaResultMapper rm = new JpaResultMapper();
        BoardResponse.DetailDTO detailDTO = rm.uniqueResult(query, BoardResponse.DetailDTO.class); // 하나만 하는 것이 uniqueResult(), 여러 개는 list()
        return detailDTO;

        // query.getSingleResult(); // Entity가 아니라서 컬럼별 타입을 모른다. Row 하나가 오브젝트 배열의 타입이다.

    }
}
