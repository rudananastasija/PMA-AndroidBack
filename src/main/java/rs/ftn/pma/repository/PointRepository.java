package rs.ftn.pma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ftn.pma.model.Point;

@Repository
public interface PointRepository extends JpaRepository<Point, Long> {
}
