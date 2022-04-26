package fixel.cs.repository;


import fixel.cs.entity.Question;

import fixel.cs.type.StatusCd;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;




@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    // 해결된 요청사항 가져오기(상태코드가 CLOSE)
    Page<Question> findAllByStatusCd(StatusCd statusCd, Pageable pageable);

    Page<Question> findAllByStatusCdIsNot(StatusCd statusCd, Pageable pageable);


}
