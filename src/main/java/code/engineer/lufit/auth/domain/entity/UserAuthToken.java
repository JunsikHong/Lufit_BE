package code.engineer.lufit.auth.domain.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class UserAuthToken {

    private Long id;
    private Users user;
    private UserDevice device;
    private String token;
    private Boolean isRevoked;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
}
