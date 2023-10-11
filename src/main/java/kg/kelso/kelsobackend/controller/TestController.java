package kg.kelso.kelsobackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import kg.kelso.kelsobackend.entities.user.User;
import kg.kelso.kelsobackend.service.auth.AuthService;
import kg.kelso.kelsobackend.util.exception.NotFoundException;
import kg.kelso.kelsobackend.util.security.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
@Slf4j
public class TestController {

    @Autowired
    AuthService service;

    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public String userAccess() {
        return "User Content.";
    }

    @GetMapping("/mod")
    @PreAuthorize("hasRole('MODERATOR')")
    public String moderatorAccess() {
        return "Moderator Board.";
    }

    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public User adminAccess() throws NotFoundException {
        // Get the current authentication object
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info(String.valueOf(new Time(System.currentTimeMillis())));
        // Check if the user is authenticated
        if (authentication.isAuthenticated()) {
            // Get the current user's username (if available)
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetailsImpl userDetails) {
                return service.getById(userDetails.getId());
            } else {
                // Handle the case where the principal is not a UserDetails instance
                return null;
            }
        } else {
            // Handle the case where the user is not authenticated
            return null;
        }
    }
}
