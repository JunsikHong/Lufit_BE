package code.engineer.lufit.auth.domain.repository;

import code.engineer.lufit.auth.domain.entity.UserAuthProvider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAuthProviderRepository extends JpaRepository<UserAuthProvider, Long> {
}
