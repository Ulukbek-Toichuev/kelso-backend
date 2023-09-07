package kg.kelso.kelsobackend.service.auth;


import kg.kelso.kelsobackend.models.user.LoginRequest;
import kg.kelso.kelsobackend.models.user.PasswordResetRequest;
import kg.kelso.kelsobackend.models.user.SignupRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<?> signIn(LoginRequest loginRequest);

    ResponseEntity<?> signUp(SignupRequest signUpRequest);

    ResponseEntity<?> logout();

    ResponseEntity<?> resetPassword(PasswordResetRequest passwordResetRequest);

}
