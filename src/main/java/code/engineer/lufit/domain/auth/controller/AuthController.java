package code.engineer.lufit.domain.auth.controller;

import code.engineer.lufit.domain.auth.dto.request.GuestLoginRequest;
import code.engineer.lufit.domain.auth.dto.request.LocalLoginRequest;
import code.engineer.lufit.domain.auth.dto.request.RefreshTokenRequest;
import code.engineer.lufit.domain.auth.dto.request.SignUpRequest;
import code.engineer.lufit.domain.auth.dto.response.TokenResponse;
import code.engineer.lufit.domain.auth.service.AuthService;
import code.engineer.lufit.global.dto.response.ApiResponse;
import code.engineer.lufit.global.security.principal.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/guest")
    public ResponseEntity<ApiResponse<TokenResponse>> guestLogin(@RequestBody GuestLoginRequest request) {
        return ResponseEntity.ok(ApiResponse.ok("GUEST_LOGIN", authService.guestLogin(request)));
    }

    @PostMapping("/signUp")
    public ResponseEntity<ApiResponse<TokenResponse>> signUp(@RequestBody SignUpRequest request) {
        return ResponseEntity.ok(ApiResponse.ok("SIGNUP", authService.localSignUp(request)));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<TokenResponse>> login(@RequestBody LocalLoginRequest request) {
        return ResponseEntity.ok(ApiResponse.ok("USER_LOGIN", authService.localLogin(request)));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<TokenResponse>> refresh(@RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(ApiResponse.ok("REFRESH_TOKEN", authService.refreshTokens(request)));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam Long deviceId
            ) {
        authService.logout(userDetails.getUserId(), deviceId);
        return  ResponseEntity.ok(ApiResponse.ok("LOGOUT", null));
    }

    @PostMapping("/logout/all")
    public ResponseEntity<ApiResponse<Void>> logoutAll(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        authService.logoutAll(userDetails.getUserId());
        return ResponseEntity.ok(ApiResponse.ok("LOGOUT_ALL", null));
    }
}
