package code.engineer.lufit.auth.domain.repository;

import code.engineer.lufit.auth.domain.entity.UserAuthToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAuthTokenRepository extends JpaRepository<UserAuthToken, Long> {
}
