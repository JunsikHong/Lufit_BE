package code.engineer.lufit.domain.auth.entity;

import code.engineer.lufit.domain.user.entity.Users;
import code.engineer.lufit.domain.auth.enums.Provider;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_auth_providers")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserAuthProvider {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @Column(name = "provider", length = 10)
    private Provider provider;

    @Column(name = "provider_uuid", length = 500)
    private String providerUuid;

    @Column(name = "email", length = 500)
    private String email;

    @Column(name = "password", length = 500)
    private String password;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private UserAuthProvider(Users user, Provider provider, String providerUuid, String email, String password) {
        this.user = user;
        this.provider = provider;
        this.providerUuid = providerUuid;
        this.email = email;
        this.password = password;
    }

    public static UserAuthProvider createLocalUser(Users user, String providerUuid, String email, String password) {
        return new UserAuthProvider(user, Provider.LOCAL, providerUuid, email, password);
    }

    public static UserAuthProvider createKakaoUser(Users user, String providerUuid, String email, String password) {
        return new UserAuthProvider(user, Provider.KAKAO, providerUuid, email, password);
    }

    public static UserAuthProvider createNaverUser(Users user, String providerUuid, String email, String password) {
        return new UserAuthProvider(user, Provider.NAVER, providerUuid, email, password);
    }

    public static UserAuthProvider createGoogleUser(Users user, String providerUuid, String email, String password) {
        return new UserAuthProvider(user, Provider.GOOGLE, providerUuid, email, password);
    }

    public static UserAuthProvider createAppleUser(Users user, String providerUuid, String email, String password) {
        return new UserAuthProvider(user, Provider.APPLE, providerUuid, email, password);
    }
}
