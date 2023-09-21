package kg.kelso.kelsobackend.service.auth;

import kg.kelso.kelsobackend.dao.RoleDao;
import kg.kelso.kelsobackend.dao.UserDao;
import kg.kelso.kelsobackend.entities.user.Role;
import kg.kelso.kelsobackend.entities.user.User;
import kg.kelso.kelsobackend.enums.ERole;
import kg.kelso.kelsobackend.model.message.MessageResponse;
import kg.kelso.kelsobackend.model.user.*;
import kg.kelso.kelsobackend.util.exception.NotFoundException;
import kg.kelso.kelsobackend.util.security.UserDetailsImpl;
import kg.kelso.kelsobackend.util.security.jwt.JwtUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthServiceImpl implements AuthService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserDao userDao;

    @Autowired
    RoleDao roleDao;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Override
    public ResponseEntity<?> signIn(LoginRequest loginRequest){

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                    .body(new UserInfoResponse(
                            userDetails.getId(),
                            userDetails.getUsername(),
                            userDetails.getEmail(),
                            roles
                    ));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new MessageResponse("Authentication failed: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("An error occurred during authentication: " + e.getMessage()));
        }

    }

    @Override
    public ResponseEntity<?> signUp(SignupRequest signUpRequest){
        if (userDao.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userDao.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        User user = mapSignUpToUser(signUpRequest);
        user.setRoles(mapRoles(signUpRequest.getRoles()));
        userDao.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @Override
    public ResponseEntity<?> logout(){
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE
                        , jwtUtils.getCleanJwtCookie().toString())
                .body(new MessageResponse("You've been signed out!"));
    }

    @Override
    public ResponseEntity<?> resetPassword(PasswordResetRequest passwordResetRequest){
        Optional<User> optionalUser = userDao.findById(passwordResetRequest.getUserId());

        if (optionalUser.isEmpty()) {
            return ResponseEntity.badRequest().body(
                    new MessageResponse("User with id: " + passwordResetRequest.getUserId() + " not found"));
        }else{
            userDao.updateEmailByUsername(passwordResetRequest.getUserId()
                    , encoder.encode(passwordResetRequest.getPassword()));
            return ResponseEntity.ok(new MessageResponse("Successfully update password"));
        }
    }

    public User getById(Long id) throws NotFoundException {
        return userDao.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }

    private User mapSignUpToUser(SignupRequest signUpRequest){
       return new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()),
                signUpRequest.getPhoneNum(),
               new Timestamp(System.currentTimeMillis()),
               false
       );
    }

    private Set<Role> mapRoles(Set<String> strRoles){
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleDao.findByRole(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(log::info);
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin" -> {
                        Role adminRole = roleDao.findByRole(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                    }
                    case "mod" -> {
                        Role modRole = roleDao.findByRole(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);
                    }
                    default -> {
                        Role userRole = roleDao.findByRole(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                    }
                }
            });
        }

        return roles;
    }

}
