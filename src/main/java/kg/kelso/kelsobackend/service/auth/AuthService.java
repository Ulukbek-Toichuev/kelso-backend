package kg.kelso.kelsobackend.service.auth;


import kg.kelso.kelsobackend.models.LoginRequest;
import kg.kelso.kelsobackend.models.MessageResponse;
import kg.kelso.kelsobackend.models.PasswordResetRequest;
import kg.kelso.kelsobackend.models.SignupRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<?> signIn(LoginRequest loginRequest);

    ResponseEntity<?> signUp(SignupRequest signUpRequest);

    ResponseEntity<?> logout();

    ResponseEntity<?> resetPassword(PasswordResetRequest passwordResetRequest);

}
