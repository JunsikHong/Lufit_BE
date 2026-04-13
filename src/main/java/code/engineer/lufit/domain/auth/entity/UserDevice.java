package code.engineer.lufit.domain.auth.entity;

import code.engineer.lufit.domain.user.entity.Users;
import code.engineer.lufit.domain.auth.enums.DeviceType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_devices")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDevice {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @Column(name = "device_type", length = 10)
    @Enumerated(EnumType.STRING)
    private DeviceType type;

    @Column(name = "device_info", length = 500)
    private String info;

    @Column(name = "device_uuid", length = 500)
    private String deviceUuid;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private UserDevice(Users user, DeviceType type, String info, String deviceUuid) {
        this.user = user;
        this.type = type;
        this.info = info;
        this.deviceUuid = deviceUuid;
    }

    public static UserDevice create(Users user, String deviceUuid, DeviceType type, String info) {
        UserDevice userDevice = new UserDevice();
        userDevice.user = user;
        userDevice.deviceUuid = deviceUuid;
        userDevice.type = type;
        userDevice.info = info;
        return userDevice;
    }

    public static UserDevice createIOS(Users user, String info, String deviceUuid) {
        return new UserDevice(user, DeviceType.APP_IOS, info, deviceUuid);
    }

    public static UserDevice createAOS(Users user, String info, String deviceUuid) {
        return new UserDevice(user, DeviceType.APP_AOS, info, deviceUuid);
    }

    public static UserDevice createWeb(Users user, String info, String deviceUuid) {
        return new UserDevice(user, DeviceType.WEB, info, deviceUuid);
    }
}
