package yemensoft.articles_app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

@RestController 
@RequestMapping("/api/comments")
@CrossOrigin(origins = "http://localhost:4200")

public class CommentsController {

    @Autowired
    private CommentsRepository commentRepository;
    
    @PostMapping
    public Comments createComment(@RequestBody Comments comment) {
        return commentRepository.save(comment);  
    }

     @GetMapping("/article/{articleId}")
    public List<Comments> getCommentsByArticle(@PathVariable Long articleId) {
        return commentRepository.findByArticleId(articleId);
    }
    
    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id) {
        commentRepository.deleteById(id);
    }
}
