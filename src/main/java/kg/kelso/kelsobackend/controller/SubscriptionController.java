package kg.kelso.kelsobackend.controller;


import kg.kelso.kelsobackend.model.subscription.SubscribeModelRequest;
import kg.kelso.kelsobackend.model.subscription.SubscribeModelResponse;
import kg.kelso.kelsobackend.model.message.MessageResponse;
import kg.kelso.kelsobackend.service.subscribe.SubscribeService;
import kg.kelso.kelsobackend.util.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/subscription")
public class SubscriptionController {

    @Autowired
    SubscribeService service;
    @PostMapping("/save")
    public ResponseEntity<MessageResponse> subscribe(@RequestBody SubscribeModelRequest model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetailsImpl userDetails) {
                try {
                    model.setUserId(userDetails.getId());
                    service.saveSubscribe(model);
                    return ResponseEntity.ok(new MessageResponse("Subscription saved successfully"));
                } catch (Exception e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("An error occurred"));
                }
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse("User not authenticated"));
    }

    @GetMapping("/active/{id}")
    public ResponseEntity<?> getActiveSubscribeByUserId(@PathVariable Long id) {
        SubscribeModelResponse response = service.getActiveSubscribeByUserId(id);
        return response != null ?
                ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Subscribe not found"));
    }


}
