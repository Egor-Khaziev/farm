package shell.server.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shell.api.utils.AuthMessage;
import shell.api.utils.Credential;
import shell.server.core.services.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    public AuthMessage login(@RequestBody Credential credential) {
            return  userService.login(credential);
    }

    @PostMapping("")
    public AuthMessage registration(@RequestBody Credential credential) {
            return userService.registration(credential);
    }

    @PostMapping("/quit")
    public AuthMessage quit(@RequestBody String token) {
        return userService.quit(token);
    }

}



