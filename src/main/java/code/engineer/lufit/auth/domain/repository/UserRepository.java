package code.engineer.lufit.auth.domain.repository;

import code.engineer.lufit.auth.domain.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {
}
