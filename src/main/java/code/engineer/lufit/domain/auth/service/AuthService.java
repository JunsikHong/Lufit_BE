package code.engineer.lufit.domain.auth.service;

import code.engineer.lufit.domain.auth.entity.UserDevice;
import code.engineer.lufit.domain.auth.repository.UserAuthProviderRepository;
import code.engineer.lufit.domain.auth.repository.UserAuthTokenRepository;
import code.engineer.lufit.domain.auth.repository.UserDeviceRepository;
import code.engineer.lufit.domain.user.repository.UserRepository;
import code.engineer.lufit.global.security.token.JwtProvider;
import com.nimbusds.oauth2.sdk.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final UserAuthProviderRepository userAuthProviderRepository;
    private final UserDeviceRepository userDeviceRepository;
    private final UserAuthTokenRepository userAuthTokenRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;


}
