package rs.ftn.pma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rs.ftn.pma.model.Route;
import rs.ftn.pma.model.User;

import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Route,Long> {
    @Query("select r from Route r where r.user = :user ORDER BY r.id DESC")
    List<Route> findRoutesOfUser(@Param("user") User user);
}
