package org.example.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "tbl_users")
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Returns the authorities granted to the user. Cannot return <code>null</code>.
     * @return the authorities, sorted by natural key (never <code>null</code>)
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    /**
     * Returns the password used to authenticate the user.
     */
    @Column(name = "password", length = 500, nullable = false)
    private String password;

    /**
     * Returns the username used to authenticate the user. Cannot return
     * <code>null</code>.
     */
    @Column(name = "username", length = 500, nullable = false)
    private String username;

    /**
     * Indicates whether the user's account has expired. An expired account cannot be
     * authenticated.
     */
    @Column(name = "account_non_expired", nullable = false)
    private boolean isAccountNonExpired;

    /**
     * Indicates whether the user is locked or unlocked. A locked user cannot be
     * authenticated.
     */
    @Column(name = "account_non_locked", nullable = false)
    private boolean isAccountNonLocked;

    /**
     * Indicates whether the user's credentials (password) has expired. Expired
     * credentials prevent authentication.
     */
    @Column(name = "credentials_non_expired", nullable = false)
    private boolean isCredentialsNonExpired;

    /**
     * Indicates whether the user is enabled or disabled. A disabled user cannot be
     * authenticated.
     */
    @Column(name = "enabled", nullable = false)
    private boolean isEnabled;
}
