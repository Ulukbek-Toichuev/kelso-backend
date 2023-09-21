package kg.kelso.kelsobackend.controller;

import jakarta.validation.Valid;
import kg.kelso.kelsobackend.model.user.LoginRequest;
import kg.kelso.kelsobackend.model.user.PasswordResetRequest;
import kg.kelso.kelsobackend.model.user.SignupRequest;
import kg.kelso.kelsobackend.service.auth.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody @Valid LoginRequest loginRequest) {
        return authService.signIn(loginRequest);
    }


    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody SignupRequest signUpRequest) {
        return authService.signUp(signUpRequest);
    }

    @PostMapping("/sign-out")
    public ResponseEntity<?> logoutUser() {
        return authService.logout();
    }

    @PostMapping("/reset-password")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> resetPassword(@RequestBody PasswordResetRequest passwordResetRequest){
        return authService.resetPassword(passwordResetRequest);
    }
}
