package yemensoft.articles_app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yemensoft.articles_app.dto.UserResponse;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired private UserRepository userRepository;

    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(u -> new UserResponse(u.getId(), u.getUsername(), u.getFullName()))
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(u -> ResponseEntity.ok(new UserResponse(u.getId(), u.getUsername(), u.getFullName())))
                .orElse(ResponseEntity.notFound().build());
    }
}