package rs.ftn.pma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ftn.pma.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findOneById(Long id);
    User findOneByUsername(String username);
}
