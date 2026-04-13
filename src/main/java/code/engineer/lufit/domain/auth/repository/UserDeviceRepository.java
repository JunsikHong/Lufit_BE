package code.engineer.lufit.domain.auth.repository;

import code.engineer.lufit.domain.auth.entity.UserDevice;
import code.engineer.lufit.domain.auth.enums.DeviceType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDeviceRepository extends JpaRepository<UserDevice, Long> {
    Optional<UserDevice> findByDeviceUuidAndDeviceType(String deviceUuid, DeviceType deviceType);
}
