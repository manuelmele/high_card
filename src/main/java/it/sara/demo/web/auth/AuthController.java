package it.sara.demo.web.auth;

import it.sara.demo.exception.GenericException;
import it.sara.demo.service.auth.AuthService;
import it.sara.demo.web.user.request.LoginRequest;
import it.sara.demo.web.user.response.LoginResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) throws GenericException {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}
