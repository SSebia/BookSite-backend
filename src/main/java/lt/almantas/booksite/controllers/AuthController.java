package lt.almantas.booksite.controllers;

import lt.almantas.booksite.model.dto.UserLoginDTO;
import lt.almantas.booksite.model.dto.UserRegisterDTO;
import lt.almantas.booksite.model.fdto.UserLoginFDTO;
import lt.almantas.booksite.model.fdto.UserRegisterFDTO;
import lt.almantas.booksite.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/auth")
@Validated
public class AuthController {
    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginDTO> userLogin(@RequestBody UserLoginFDTO userLoginFDTO) {
        UserLoginDTO loggedUser = userService.authUser(userLoginFDTO);
        if (loggedUser.getToken() == null) {
            return ResponseEntity.badRequest().body(loggedUser);
        }
        return ResponseEntity.ok(loggedUser);
    }

    @PostMapping("/register")
    public ResponseEntity<UserRegisterDTO> userRegister(@RequestBody UserRegisterFDTO userRegisterFDTO) {
        UserRegisterDTO addedUser = userService.registerUser(userRegisterFDTO);
        if (!addedUser.isSuccess()) {
            return ResponseEntity.badRequest().body(addedUser);
        }
        return ResponseEntity.ok(addedUser);
    }
}