package yemensoft.articles_app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



@RestController 
@RequestMapping ("/api/auth")
@CrossOrigin (origins =  "http://localhost:4200")
public class AuthController {

    @Autowired 
    private UserRepository userRepository;

    @PostMapping("/register")
    public Users register(@RequestBody Users users) {
        //TODO: process POST request
        
        return userRepository.save(users);
    }

    @PostMapping("/login")
    public Users login(@RequestBody LoginRequest request) {
        //TODO: process POST request
        
        return userRepository.findByUsername(request.getUsername())
        .filter(username -> username.getPassword().equals(request.getPassword()))
        .orElse(null);
    }
    
    @GetMapping("/{id}")
    public Users getUser(@PathVariable Long id) {
        return userRepository.findById(id).orElse(null);
    }
    
    class LoginRequest {
    private String username;
    private String password;
    
    public LoginRequest() {}
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
    
}
