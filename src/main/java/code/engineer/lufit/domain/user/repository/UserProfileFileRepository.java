package code.engineer.lufit.domain.user.repository;

import code.engineer.lufit.domain.user.entity.UserProfileFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileFileRepository extends JpaRepository<UserProfileFile, Long> {
}
