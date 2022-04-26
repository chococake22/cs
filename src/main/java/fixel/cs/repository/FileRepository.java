package fixel.cs.repository;

import fixel.cs.entity.AttachedFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<AttachedFile, Long> {

    List<AttachedFile> findAllByQuestionQuestionNo(Long no);

}
