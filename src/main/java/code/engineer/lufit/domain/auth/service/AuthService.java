package code.engineer.lufit.domain.auth.service;

import code.engineer.lufit.domain.auth.dto.request.GuestLoginRequest;
import code.engineer.lufit.domain.auth.dto.request.LocalLoginRequest;
import code.engineer.lufit.domain.auth.dto.request.RefreshTokenRequest;
import code.engineer.lufit.domain.auth.dto.request.SignUpRequest;
import code.engineer.lufit.domain.auth.dto.response.TokenResponse;
import code.engineer.lufit.domain.auth.entity.UserAuthProvider;
import code.engineer.lufit.domain.auth.entity.UserAuthToken;
import code.engineer.lufit.domain.auth.entity.UserDevice;
import code.engineer.lufit.domain.auth.enums.Provider;
import code.engineer.lufit.domain.auth.exception.AuthException;
import code.engineer.lufit.domain.auth.repository.UserAuthProviderRepository;
import code.engineer.lufit.domain.auth.repository.UserAuthTokenRepository;
import code.engineer.lufit.domain.auth.repository.UserDeviceRepository;
import code.engineer.lufit.domain.user.entity.Users;
import code.engineer.lufit.domain.user.repository.UserRepository;
import code.engineer.lufit.global.security.token.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final UserAuthProviderRepository userAuthProviderRepository;
    private final UserDeviceRepository userDeviceRepository;
    private final UserAuthTokenRepository userAuthTokenRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    // 비회원 로그인
    @Transactional
    public TokenResponse guestLogin(GuestLoginRequest request) {
        UserDevice userDevice = userDeviceRepository
                .findByDeviceUuidAndDeviceType(request.getDeviceUuid(), request.getDeviceType())
                .orElseGet(() -> {
                    Users guest = userRepository.save(Users.createGuest());
                    return userDeviceRepository.save(UserDevice.create(guest, request.getDeviceUuid(), request.getDeviceType(), request.getDeviceInfo()));
                });

        Users user = userDevice.getUser();
        validateUserStatus(user);
        return issueTokens(user, userDevice);
    }

    // 일반 회원가입
    @Transactional
    public TokenResponse localSignUp (SignUpRequest request) {
        if(userAuthProviderRepository.existByProviderAndEmail(Provider.LOCAL, request.getEmail())) {
            throw  new IllegalArgumentException("이미 사용중인 이메일입니다.");
        }

        Users user;
        UserDevice userDevice;

        if(request.getGuestUserId() != null) {
            user = userRepository.findById(request.getGuestUserId())
                    .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 비회원 정보입니다."));
            user.promoteToUser();

            userDevice = userDeviceRepository
                    .findByDeviceUuidAndDeviceType(request.getDeviceUuid(), request.getDeviceType())
                    .orElseGet(() -> userDeviceRepository.save(
                            UserDevice.create(user, request.getDeviceUuid(), request.getDeviceType(), request.getDeviceInfo())
                    ));
        } else {
            user = userRepository.save(Users.createUser());
            userDevice=  userDeviceRepository.save(
                    UserDevice.create(user, request.getDeviceUuid(), request.getDeviceType(), request.getDeviceInfo())
            );
        }

        String encodedPw = passwordEncoder.encode(request.getPassword());
        userAuthProviderRepository.save(UserAuthProvider.createLocalUser(user, request.getEmail(), encodedPw));
        return issueTokens(user, userDevice);
    }

    @Transactional
    public TokenResponse localLogin (LocalLoginRequest request) {
        UserAuthProvider userAuthProvider = userAuthProviderRepository
                .findByProviderAndEmail(Provider.LOCAL, request.getEmail())
                .orElseThrow(() -> new AuthException("이메일 또는 비밀번호가 올바르지 않습니다."));

        if(!passwordEncoder.matches(request.getPassword(), userAuthProvider.getPassword())) {
            throw new AuthException("이메일 또는 비밀번호가 올바르지 않습니다.");
        }

        Users user = userAuthProvider.getUser();
        validateUserStatus(user);

        UserDevice userDevice = userDeviceRepository
                .findByDeviceUuidAndDeviceType(request.getDeviceUuid(), request.getDeviceType())
                .orElseGet(() -> userDeviceRepository.save(
                        UserDevice.create(user, request.getDeviceUuid(), request.getDeviceType(), request.getDeviceInfo())
                ));

        return issueTokens(user, userDevice);
    }

    @Transactional
    public TokenResponse refreshTokens(RefreshTokenRequest request) {
        if(!jwtProvider.validateToken(request.getRefreshToken())) {
            throw new AuthException("유효하지 않은 Refresh Token 입니다.");
        }

        UserAuthToken userAuthToken = userAuthTokenRepository
                .findByToken(request.getRefreshToken())
                .orElseThrow(() -> new AuthException("존재하지 않는 Refresh Token 입니다."));

        if(!userAuthToken.isValid()) {
            if(userAuthToken.getIsRevoked()) {
                userAuthTokenRepository.findAllByUser_IdAndIsRevokedFalse(userAuthToken.getUser().getId())
                        .forEach(t -> { t.revoke(); userAuthTokenRepository.save(t); });
            }
            throw new AuthException("만료되었거나 폐기된 Refresh Token 입니다.");
        }

        userAuthToken.revoke();
        userAuthTokenRepository.save(userAuthToken);

        Users user = userAuthToken.getUser();
        UserDevice userDevice = userAuthToken.getUserDevice();

        return issueTokens(user, userDevice);
    }

    @Transactional
    public void logout(Long userId, Long deviceId) {
        userAuthTokenRepository.findByUser_IdAndDevice_IdAndIsRevokedFalse(userId, deviceId)
                .ifPresent(t -> { t.revoke(); userAuthTokenRepository.save(t); });
    }

    @Transactional
    public void logoutAll(Long userId) {
        userAuthTokenRepository.findAllByUser_IdAndIsRevokedFalse(userId)
                .forEach(t -> { t.revoke(); userAuthTokenRepository.save(t); });
    }

    private TokenResponse issueTokens(Users user, UserDevice userDevice) {
        userAuthTokenRepository.findByUser_IdAndDevice_IdAndIsRevokedFalse(user.getId(), userDevice.getId())
                .ifPresent(t -> {t.revoke(); userAuthTokenRepository.save(t); });

        String accessToken = jwtProvider.generateAccessToken(user.getId(), user.getType().name());
        String refreshToken = jwtProvider.generateRefreshToken(user.getId());

        LocalDateTime expiresAt = LocalDateTime.now()
                .plusSeconds(jwtProvider.getRefreshTokenExpiry() / 1000);

        userAuthTokenRepository.save(UserAuthToken.create(user, userDevice, refreshToken, expiresAt));

        return new TokenResponse(accessToken, refreshToken, user.getType().name());
    }

    private void validateUserStatus(Users user) {
        if(user.isBanned()) {
            throw new AuthException("정지된 계정입니다.");
        }
    }

}
