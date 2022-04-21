package fixel.cs.repository;


import fixel.cs.entity.Request;

import fixel.cs.type.StatusCd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    // 해결된 요청사항 가져오기(상태코드가 CLOSE)
    List<Request> findAllByStatusCd(StatusCd statusCd);

}
