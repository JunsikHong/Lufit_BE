package code.engineer.lufit.domain.user.repository;

import code.engineer.lufit.domain.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {
}
