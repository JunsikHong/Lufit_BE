package code.engineer.lufit.global.security.config;

import code.engineer.lufit.global.security.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        //비회원 로그인 (기기등록)
                        .requestMatchers("/api/v1/auth/guest").permitAll()
                        //일반 로그인, oauth2 로그인 -> GUEST
                        .requestMatchers("/api/v1/auth/login", "/api/v1/auth/oauth2/login").hasRole("GUEST")
                        //일반 회원가입, oauth2 회원가입 -> GUEST
                        .requestMatchers("/api/v1/auth/signup", "/api/v1/auth/oauth2/signup").hasRole("GUEST")
                        //건강습관 입력, 추천 기능 -> GUEST, USER
                        .requestMatchers("/api/v1/health/**").hasAnyRole("GUEST", "USER")
                        //회원 프로필 CRUD기능, 건강습관 스케쥴러 기능 -> USER
                        .requestMatchers("/api/v1/user/**", "/api/v1/schedule/**").hasRole("USER")
                        //그 외 요청은 없음
                        .anyRequest().authenticated())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}