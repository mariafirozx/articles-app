package yemensoft.articles_app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import yemensoft.articles_app.dto.*;
import yemensoft.articles_app.security.JwtUtil;

@RestController
@RequestMapping ("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        if (userRepository.existsByUsername(req.getUsername())) {
            return ResponseEntity.badRequest().body("Username already taken");
        }
        if (userRepository.existsByEmail(req.getEmail())) {
            return ResponseEntity.badRequest().body("Email already registered");
        }

        Users user = new Users(req.getUsername(), req.getEmail(),
                passwordEncoder.encode(req.getPassword()), req.getFullName());
        user = userRepository.save(user);

        String token = jwtUtil.generateToken(user.getUsername());
        return ResponseEntity.ok(new AuthResponse(token, user.getId(), user.getUsername()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        return userRepository.findByUsername(req.getUsername())
                .filter(u -> passwordEncoder.matches(req.getPassword(), u.getPassword()))
                .map(u -> ResponseEntity.ok(
                        new AuthResponse(jwtUtil.generateToken(u.getUsername()), u.getId(), u.getUsername())))
                .orElse(ResponseEntity.status(401).body(null));
    }
}