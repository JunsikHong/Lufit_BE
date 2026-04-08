package code.engineer.lufit.domain.auth.repository;

import code.engineer.lufit.domain.auth.entity.UserDevice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDeviceRepository extends JpaRepository<UserDevice, Long> {
}
