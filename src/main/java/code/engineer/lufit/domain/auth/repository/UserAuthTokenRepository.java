package code.engineer.lufit.domain.auth.repository;

import code.engineer.lufit.domain.auth.entity.UserAuthToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserAuthTokenRepository extends JpaRepository<UserAuthToken, Long> {
    Optional<UserAuthToken> findByToken(String token);
    List<UserAuthToken> findAllByUser_IdAndIsRevokedFalse(Long userId);
    Optional<UserAuthToken> findByUser_IdAndDevice_IdAndIsRevokedFalse(Long userId, Long deviceId);
}
