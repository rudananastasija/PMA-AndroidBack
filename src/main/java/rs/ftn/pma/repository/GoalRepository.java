package rs.ftn.pma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rs.ftn.pma.model.Goals;
import rs.ftn.pma.model.Route;
import rs.ftn.pma.model.User;

import java.util.List;

@Repository
public interface GoalRepository extends JpaRepository<Goals, Long> {
    Goals findOneById(Long id);
    @Query("select g from Goals g where g.user = :user ORDER BY g.id DESC")
    List<Goals> findGoalsOfUser(@Param("user") User user);

}
