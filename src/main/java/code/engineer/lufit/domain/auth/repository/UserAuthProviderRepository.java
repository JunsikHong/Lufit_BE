package code.engineer.lufit.domain.auth.repository;

import code.engineer.lufit.domain.auth.entity.UserAuthProvider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAuthProviderRepository extends JpaRepository<UserAuthProvider, Long> {
}
