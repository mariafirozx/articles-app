package yemensoft.articles_app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import yemensoft.articles_app.dto.CommentRequest;
import yemensoft.articles_app.dto.CommentResponse;
import yemensoft.articles_app.security.CustomUserDetails;
import java.util.List;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin(origins = "http://localhost:4200")
public class CommentsController {

    @Autowired private CommentsRepository commentRepository;
    @Autowired private ArticlesRepository articleRepository;

    private CommentResponse toResponse(Comments c) {
        return new CommentResponse(c.getId(), c.getText(), c.getArticle().getId(),
                c.getAuthor().getId(), c.getAuthor().getUsername(), c.getCreatedAt());
    }

    @GetMapping("/article/{articleId}")
    public List<CommentResponse> getCommentsByArticle(@PathVariable Long articleId) {
        return commentRepository.findByArticleIdOrderByCreatedAtDesc(articleId)
                .stream().map(this::toResponse).toList();
    }

    @PostMapping
    public ResponseEntity<?> createComment(@RequestBody CommentRequest req, Authentication auth) {
        Articles article = articleRepository.findById(req.getArticleId()).orElse(null);
        if (article == null) return ResponseEntity.notFound().build();
        if (!"PUBLISHED".equals(article.getStatus())) {
            return ResponseEntity.badRequest().body("Cannot comment on an unpublished article");
        }
        Users user = ((CustomUserDetails) auth.getPrincipal()).getUser();
        Comments comment = new Comments(req.getText(), article, user);
        return ResponseEntity.ok(toResponse(commentRepository.save(comment)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id, Authentication auth) {
        Comments comment = commentRepository.findById(id).orElse(null);
        if (comment == null) return ResponseEntity.notFound().build();
        Users user = ((CustomUserDetails) auth.getPrincipal()).getUser();
        if (!comment.getAuthor().getId().equals(user.getId())) {
            return ResponseEntity.status(403).body("Not your comment");
        }
        commentRepository.delete(comment);
        return ResponseEntity.noContent().build();
    }
}