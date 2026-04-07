package code.engineer.lufit.auth.domain.repository;

import code.engineer.lufit.auth.domain.entity.UserDevice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDeviceRepository extends JpaRepository<UserDevice, Long> {
}
