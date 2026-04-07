package code.engineer.lufit.auth.domain.entity;

import code.engineer.lufit.auth.domain.enums.Provider;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class UserAuthProvider {

    private Long id;
    private Users user;
    private Provider provider;
    private String providerUuid;
    private String email;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
