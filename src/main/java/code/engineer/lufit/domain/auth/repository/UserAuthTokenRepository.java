package code.engineer.lufit.domain.auth.repository;

import code.engineer.lufit.domain.auth.entity.UserAuthToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAuthTokenRepository extends JpaRepository<UserAuthToken, Long> {
}
