package code.engineer.lufit.auth.domain.entity;

import code.engineer.lufit.auth.domain.enums.DeviceType;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class UserDevice {

    private Long id;
    private Users user;
    private String deviceUuid;
    private DeviceType deviceType;
    private String deviceInfo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
