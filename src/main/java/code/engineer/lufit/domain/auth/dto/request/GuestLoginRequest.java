package code.engineer.lufit.domain.auth.dto.request;

import code.engineer.lufit.domain.auth.enums.DeviceType;
import lombok.Getter;

@Getter
public class GuestLoginRequest {

    private String deviceUuid;
    private DeviceType deviceType;
    private String deviceInfo;

}
