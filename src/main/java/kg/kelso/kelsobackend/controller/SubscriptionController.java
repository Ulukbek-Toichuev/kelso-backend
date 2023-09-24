package kg.kelso.kelsobackend.controller;

import kg.kelso.kelsobackend.model.subscription.*;
import kg.kelso.kelsobackend.model.message.MessageResponse;
import kg.kelso.kelsobackend.service.subscribe.*;
import kg.kelso.kelsobackend.util.exception.NotFoundException;
import kg.kelso.kelsobackend.util.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/subscription")
public class SubscriptionController {

    @Autowired
    SubscribeService subscribeService;

    @PostMapping("/save")
    public ResponseEntity<MessageResponse> subscribe(@RequestBody SubscribeModelRequest model) {
        Authentication authentication = getCurrentUser();

        if (authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetailsImpl userDetails) {
                try {
                    model.setUserId(userDetails.getId());
                    subscribeService.saveSubscribe(model);
                    return ResponseEntity.ok(new MessageResponse("Subscription saved successfully"));
                } catch (Exception e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(new MessageResponse(e.getMessage()));
                }
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse("User not authenticated"));
    }

    @GetMapping("/history/{id}")
    public List<SubscribeModelResponse> getHistoryByUserId(@PathVariable Long id) {
        return subscribeService.getHistoryByUserId(id);
    }

    @GetMapping("/get-all")
    public List<SubscribeModelResponse> getAll() {
        return subscribeService.getAll();
    }

    @GetMapping()
    public List<SubscribeModelResponse> getByStatus(@RequestParam String status) {
        return subscribeService.getByStatus(status);
    }

    @GetMapping("/update-status")
    public ResponseEntity<MessageResponse> updateStatus(@RequestParam String status, @RequestParam Long userId) {
        try {
            String message = subscribeService.updateStatus(status, userId);
            return ResponseEntity.ok(new MessageResponse(message));
        }catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse(e.getMessage()));
        }
    }

    @GetMapping("/active/{id}")
    public ResponseEntity<?> getActiveSubscribeByUserId(@PathVariable Long id) {
        SubscribeModelResponse response = subscribeService.getActiveSubscribeByUserId(id);
        return response != null ?
                ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Subscribe not found"));
    }

    @PostMapping("/get-book")
    public ResponseEntity<MessageResponse> getBook(@RequestBody List<SubscribeDetailsModel> models) {
        Authentication authentication = getCurrentUser();
        if (authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetailsImpl userDetails) {
                try {
                    return subscribeService.saveBooking(models, userDetails.getId());
                } catch (Exception e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("An error occurred"));
                }
            }
        }
        return null;
    }

    private Authentication getCurrentUser(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

}
