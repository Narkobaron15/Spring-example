package org.example.services.implementations;

import jakarta.annotation.Nonnull;
import org.example.dto.account.AuthResponseDTO;
import org.example.dto.account.LoginDTO;
import org.example.dto.account.RegisterDTO;
import org.example.entities.user.UserEntity;
import org.example.mappers.AccountMapper;
import org.example.repositories.UserRepo;
import org.example.services.generic.AccountService;
import org.example.services.generic.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AccountMapper accountMapper;

    @Autowired
    public AccountServiceImpl(UserRepo userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AccountMapper accountMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.accountMapper = accountMapper;
    }

    @Nonnull
    @Override
    public AuthResponseDTO login(LoginDTO request) {
        UserEntity user = userRepository.findByUsername(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        boolean isValid = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!isValid) {
            throw new UsernameNotFoundException("User not found");
        }

        AuthResponseDTO response = new AuthResponseDTO();
        response.setToken(jwtService.generateAccessToken(user));
        return response;
    }

    @Override
    public void register(RegisterDTO request) {
        Optional<UserEntity> user = userRepository.findByUsername(request.getEmail());
        if (user.isPresent()) {
            throw new UsernameNotFoundException("Дана пошта зареєстрована!");
        }

        UserEntity newUser = accountMapper.itemDtoToUser(request);
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(newUser);
    }
}
