package fixel.cs.repository;

import fixel.cs.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User getByUserEmail(String userEmail);

    Optional<User> findByUserEmail(String userEmail);

    Optional<User> findByUserEmailAndPassword(String userEmail, String password);

}
