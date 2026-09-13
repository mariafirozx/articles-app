package yemensoft.articles_app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

@RestController
@RequestMapping("/api/Articles")
@CrossOrigin(origins = "http://localhost:4200") //ang frontend

public class ArticleController {
 
    @Autowired
    private ArticlesRepository articleRepository;
    
    @GetMapping
    public List<Articles> getAllArticles() {
        return articleRepository.findAll();  
    }
    
    @GetMapping("/{id}")
    public Articles getArticleById(@PathVariable Long id) {
        return articleRepository.findById(id).orElse(null);
    }
    
    @PostMapping
    public Articles createArticle(@RequestBody Articles article) {
        return articleRepository.save(article);  
    }

    @PutMapping("/{id}")
    public Articles updateArticle(@PathVariable Long id, @RequestBody Articles article) {
        return articleRepository.save(article); //save the article
    }
    
    @DeleteMapping("/{id}")
    public void deleteArticle(@PathVariable Long id) {
        articleRepository.deleteById(id); 
    }
}
