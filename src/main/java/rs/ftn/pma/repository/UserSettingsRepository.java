package rs.ftn.pma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ftn.pma.model.UserSettings;

@Repository
public interface UserSettingsRepository extends JpaRepository<UserSettings, Long> {
    UserSettings findOneById(Long id);
}
