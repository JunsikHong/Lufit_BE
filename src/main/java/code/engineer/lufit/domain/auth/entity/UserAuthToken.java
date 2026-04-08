package code.engineer.lufit.domain.auth.entity;

import code.engineer.lufit.domain.user.entity.Users;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_auth_tokens")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserAuthToken {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id", nullable = false)
    private UserDevice userDevice;

    @Column(name = "token", length = 500)
    private String token;

    @Column(name = "is_revoked")
    private Boolean isRevoked;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    private UserAuthToken(Users user, UserDevice userDevice, String token, Boolean isRevoked) {
        this.user = user;
        this.userDevice = userDevice;
        this.token = token;
        this.isRevoked = false;
    }

    public void revoke() {
        this.isRevoked = true;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.expiresAt);
    }

    public boolean isValid() {
        return !isRevoked && !isExpired();
    }
}
