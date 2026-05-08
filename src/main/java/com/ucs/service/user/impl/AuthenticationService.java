package com.ucs.service.user.impl;

import com.ucs.entity.concretes.user.User;
import com.ucs.exception.BadRequestException;
import com.ucs.exception.ErrorMessageType;
import com.ucs.payload.request.LoginRequest;
import com.ucs.payload.request.UpdatePasswordRequest;
import com.ucs.payload.response.authentication.AuthResponse;
import com.ucs.repository.user.UserRepository;
import com.ucs.security.jwt.JwtUtils;
import com.ucs.security.service.UserDetailsImpl;
import com.ucs.service.helper.MethodHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final MethodHelper methodHelper;

    public AuthResponse authenticateUser(LoginRequest loginRequest) {

        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String role = userDetails.getAuthorities()
                .stream().findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse(null);

        AuthResponse.AuthResponseBuilder authResponse = AuthResponse.builder();
        authResponse.username(userDetails.getUsername());

        authResponse.token("Bearer " + token);

        authResponse.name(userDetails.getName());
        authResponse.userRole(role);

        return authResponse.build();
    }

    @Transactional
    public void updatePassword(UpdatePasswordRequest updatePasswordRequest) {

        UserDetailsImpl authenticatedUser = methodHelper.getAuthenticatedUserDetails();
        User user = methodHelper.getUserByUsername(authenticatedUser.getUsername());

        if (!passwordEncoder.matches(updatePasswordRequest.getOldPassword(), user.getPassword())) {
            throw new BadRequestException(ErrorMessageType.PASSWORDS_DO_NOT_MATCH);
        }

        if (passwordEncoder.matches(updatePasswordRequest.getNewPassword(), user.getPassword())) {
            throw new BadRequestException(ErrorMessageType.PASSWORD_IS_OLD);
        }

        String encodedNewPassword = passwordEncoder.encode(updatePasswordRequest.getNewPassword());
        user.setPassword(encodedNewPassword);

        userRepository.save(user);
    }
}
