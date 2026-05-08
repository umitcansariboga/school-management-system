package com.ucs.controller.user;

import com.ucs.messages.SuccessMessageType;
import com.ucs.payload.request.LoginRequest;
import com.ucs.payload.request.UpdatePasswordRequest;
import com.ucs.payload.response.authentication.AuthResponse;
import com.ucs.service.user.impl.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticationUser(@RequestBody @Valid LoginRequest loginRequest) {
        return ResponseEntity.ok(authenticationService.authenticateUser(loginRequest));
    }

    @PatchMapping("/update-password")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER','TEACHER','STUDENT')")
    public ResponseEntity<String> updatePassword(
            @RequestBody @Valid UpdatePasswordRequest updatePasswordRequest) {
        authenticationService.updatePassword(updatePasswordRequest);
        String response = SuccessMessageType.PASSWORD_CHANGED.getMessage();
        return ResponseEntity.ok(response);
    }
}
