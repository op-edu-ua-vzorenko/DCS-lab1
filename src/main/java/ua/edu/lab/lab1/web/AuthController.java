package ua.edu.lab.lab1.web;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.edu.lab.lab1.dto.AuthRequest;
import ua.edu.lab.lab1.security.JwtService;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthController(JwtService jwtService, AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );

        if (authentication.isAuthenticated()) {
            // 1. Создаем мапу для дополнительных данных (Claims)
            Map<String, Object> claims = new HashMap<>();

            // 2. Кладем туда планету, которую прислал юзер (или "Unknown", если забыл)
            String planetInfo = (authRequest.getPlanet() != null) ? authRequest.getPlanet() : "Unknown Planet";
            claims.put("planet", planetInfo);

            // 3. ВЫЗЫВАЕМ МЕТОД №2 (С claims)
            return jwtService.generateToken(authRequest.getUsername(), claims);
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }
}