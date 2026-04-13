package code.engineer.lufit.domain.auth.repository;

import code.engineer.lufit.domain.auth.entity.UserAuthProvider;
import code.engineer.lufit.domain.auth.enums.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAuthProviderRepository extends JpaRepository<UserAuthProvider, Long> {
    Optional<UserAuthProvider> findByProviderAndProviderUuid(Provider provider, String providerUuid);
    Optional<UserAuthProvider> findByProviderAndEmail(Provider provider, String email);
    boolean existByProviderAndEmail(Provider provider, String email);
}
