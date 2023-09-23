package org.example.configurations.security;

import org.example.entities.user.UserEntity;
import org.example.entities.user.UserRoleEntity;
import org.example.repositories.UserRepo;
import org.example.repositories.UserRoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;

@Configuration
public class ApplicationConfig {
    private final UserRepo userRepo;
    private final UserRoleRepo userRoleRepo;

    @Autowired
    public ApplicationConfig(UserRepo userRepo, UserRoleRepo userRoleRepo) {
        this.userRepo = userRepo;
        this.userRoleRepo = userRoleRepo;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                UserEntity userEntity = userRepo.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

                Collection<GrantedAuthority> roles = getRoles(userEntity);

                return new User(
                        userEntity.getUsername(),
                        userEntity.getPassword(),
                        roles
                );
            }

            private Collection<GrantedAuthority> getRoles(UserEntity userEntity) {
                Collection<UserRoleEntity> roles = userRoleRepo.findByUser(userEntity);
                String[] userRoles = roles.stream()
                        .map(userRole -> userRole.getRole().getName())
                        .toArray(String[]::new);
                return AuthorityUtils.createAuthorityList(userRoles);
            }
        };
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
