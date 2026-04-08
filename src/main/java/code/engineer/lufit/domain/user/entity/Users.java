package code.engineer.lufit.domain.user.entity;

import code.engineer.lufit.domain.user.enums.UserStatus;
import code.engineer.lufit.domain.user.enums.UserType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE users SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class Users {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type", length = 10)
    @Enumerated(EnumType.STRING)
    private UserType type;

    @Column(name = "status", length = 10)
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    private Users(UserType type, UserStatus status) {
        this.type = type;
        this.status = status;
    }

    public static Users createGuest() {
        return new Users(UserType.GUEST, UserStatus.ACTIVE);
    }

    public static Users createUser() {
        return new Users(UserType.USER, UserStatus.ACTIVE);
    }

    public void promoteToUser() {
        this.type = UserType.USER;
    }

    public void updateStatus(UserStatus status) {
        this.status = status;
    }

    public boolean isUser() {
        return this.type == UserType.USER;
    }

    public boolean isGuest() {
        return this.type == UserType.GUEST;
    }

    public boolean isActive() {
        return this.status == UserStatus.ACTIVE;
    }

    public boolean isInactive() {
        return this.status == UserStatus.INACTIVE;
    }

    public boolean isBanned() {
        return this.status == UserStatus.BANNED;
    }
}