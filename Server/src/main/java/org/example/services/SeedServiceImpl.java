package org.example.services;

import lombok.AllArgsConstructor;
import org.example.constants.Roles;
import org.example.entities.user.RoleEntity;
import org.example.entities.user.UserEntity;
import org.example.entities.user.UserRoleEntity;
import org.example.repositories.RoleRepo;
import org.example.repositories.UserRepo;
import org.example.repositories.UserRoleRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SeedServiceImpl implements SeedService {
    private final RoleRepo roleRepository;
    private final UserRepo userRepository;
    private final UserRoleRepo userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void seedRoleData() {
        if (roleRepository.count() == 0) {
            RoleEntity admin = RoleEntity.builder()
                    .name(Roles.Admin)
                    .build();
            roleRepository.save(admin);

            RoleEntity user = RoleEntity.builder()
                    .name(Roles.User)
                    .build();
            roleRepository.save(user);
        }
    }

    @Override
    public void seedUserData() {
        if (userRepository.count() == 0) {
            var user = UserEntity.builder()
                    .username("admin@gmail.com")
                    .firstName("Микола")
                    .lastName("Підкаблучник")
                    .phone("098 34 34 221")
                    .password(passwordEncoder.encode("123456"))
                    .build();
            userRepository.save(user);

            var role = roleRepository.findByName(Roles.Admin);
            if (role.isEmpty())
                throw new RuntimeException("Call the \"seedRoleData\" method first");

            var ur = UserRoleEntity.builder()
                    .role(role.get())
                    .user(user)
                    .build();
            userRoleRepository.save(ur);
        }
    }
}
