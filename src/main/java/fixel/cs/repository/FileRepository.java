package fixel.cs.repository;

import fixel.cs.entity.AttachedFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<AttachedFile, Long> {

    List<AttachedFile> findAllByRequestNo(Long no);

}
