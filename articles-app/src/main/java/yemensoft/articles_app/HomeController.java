package yemensoft.articles_app;

import org.springframework.web.bind.annotation.GetMapping;

public class HomeController {
     @GetMapping("/")
    public String home(){
        return "PATIENT MANAGEMENT SYSTEM";
    }

}
