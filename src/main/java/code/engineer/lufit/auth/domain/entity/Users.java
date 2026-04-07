package code.engineer.lufit.auth.domain.entity;

import code.engineer.lufit.auth.domain.enums.UserStatus;
import code.engineer.lufit.auth.domain.enums.UserType;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Users {

    private Long id;
    private UserType type;
    private UserStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
