package code.engineer.lufit.global.security.principal;

import code.engineer.lufit.domain.user.entity.Users;
import code.engineer.lufit.domain.user.enums.UserType;
import lombok.Getter;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
public class CustomUserDetails implements UserDetails {

    private final Long userId;
    private final String userType;

    public CustomUserDetails(Users user) {
        this.userId = user.getId();
        this.userType = user.getType().name();
    }

    public CustomUserDetails(Long userId, String userType) {
        this.userId = userId;
        this.userType = userType;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + userType));
    }

    @Override
    public @Nullable String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return String.valueOf(userId);
    }
}
