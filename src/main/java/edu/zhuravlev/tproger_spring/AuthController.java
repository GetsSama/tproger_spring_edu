package edu.zhuravlev.tproger_spring;

import edu.zhuravlev.tproger_spring.clients.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final ClientService clientService;
    private final TokenService tokenService;

    static {
        System.out.println("AuthController!");
    }

    @PostMapping
    public ResponseEntity<String> register(@RequestBody User user) {
        clientService.register(user.getClientId(), user.getClientSecure());
        System.out.println("Method register()!");
        return ResponseEntity.ok("Registered");
    }

    @PostMapping("/token")
    public TokenResponse getToken(@RequestBody User user) {
        clientService.checkCredentials(user.getClientId(), user.getClientSecure());
        return new TokenResponse(tokenService.generateToken(user.getClientId()));
    }

    @ExceptionHandler({RegistrationException.class, LoginException.class})
    public ResponseEntity<ErrorResponse> handleUserRegistrationException(RuntimeException ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse(ex.getMessage()));
    }
}
