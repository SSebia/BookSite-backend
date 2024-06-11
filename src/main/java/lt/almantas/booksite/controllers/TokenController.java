package lt.almantas.booksite.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/token")
@Validated
public class TokenController {
    @PostMapping("/verify")
    public ResponseEntity<Void> verifyToken() {
        // Nes JwtAuthenticationFilter jau patikrino tokeną, todėl čia nieko nedarysime
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
