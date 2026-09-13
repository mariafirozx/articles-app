package yemensoft.articles_app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import yemensoft.articles_app.dto.ArticleRequest;
import yemensoft.articles_app.dto.ArticleResponse;
import yemensoft.articles_app.security.CustomUserDetails;
import java.util.List;

@RestController
@RequestMapping("/api/articles")
@CrossOrigin(origins = "http://localhost:4200")
public class ArticleController {

    @Autowired private ArticlesRepository articleRepository;

    private ArticleResponse toResponse(Articles a) {
        return new ArticleResponse(a.getId(), a.getTitle(), a.getContent(), a.getDescription(),
                a.getStatus(), a.getAuthor().getId(), a.getAuthor().getUsername(),
                a.getCreatedAt(), a.getUpdatedAt());
    }

    private Users currentUser(Authentication auth) {
        return ((CustomUserDetails) auth.getPrincipal()).getUser();
    }

    // Public: published articles, optionally filtered by author id and/or search text
    @GetMapping
    public List<ArticleResponse> getArticles(@RequestParam(required = false) Long author,
                                              @RequestParam(required = false) String content) {
        return articleRepository.searchPublished(author, content).stream().map(this::toResponse).toList();
    }

    // Public: single article (drafts only visible to their author)
    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponse> getArticle(@PathVariable Long id, Authentication auth) {
        Articles article = articleRepository.findById(id).orElse(null);
        if (article == null) return ResponseEntity.notFound().build();

        boolean isOwner = auth != null && auth.isAuthenticated()
                && auth.getPrincipal() instanceof CustomUserDetails cud
                && cud.getId().equals(article.getAuthor().getId());

        if (!"PUBLISHED".equals(article.getStatus()) && !isOwner) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok(toResponse(article));
    }

    // Auth required: current user's own articles, any status
    @GetMapping("/my")
    public List<ArticleResponse> getMyArticles(Authentication auth) {
        Users user = currentUser(auth);
        return articleRepository.findByAuthorIdOrderByCreatedAtDesc(user.getId())
                .stream().map(this::toResponse).toList();
    }

    @PostMapping
    public ArticleResponse createArticle(@RequestBody ArticleRequest req, Authentication auth) {
        Articles article = new Articles(req.getTitle(), req.getContent(), req.getDescription(), currentUser(auth));
        return toResponse(articleRepository.save(article));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateArticle(@PathVariable Long id, @RequestBody ArticleRequest req, Authentication auth) {
        Articles article = articleRepository.findById(id).orElse(null);
        if (article == null) return ResponseEntity.notFound().build();
        if (!article.getAuthor().getId().equals(currentUser(auth).getId())) {
            return ResponseEntity.status(403).body("Not your article");
        }
        article.setTitle(req.getTitle());
        article.setContent(req.getContent());
        article.setDescription(req.getDescription());
        return ResponseEntity.ok(toResponse(articleRepository.save(article)));
    }

    @PutMapping("/{id}/publish")
    public ResponseEntity<?> publishArticle(@PathVariable Long id, Authentication auth) {
        Articles article = articleRepository.findById(id).orElse(null);
        if (article == null) return ResponseEntity.notFound().build();
        if (!article.getAuthor().getId().equals(currentUser(auth).getId())) {
            return ResponseEntity.status(403).body("Not your article");
        }
        article.setStatus("PUBLISHED");
        return ResponseEntity.ok(toResponse(articleRepository.save(article)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteArticle(@PathVariable Long id, Authentication auth) {
        Articles article = articleRepository.findById(id).orElse(null);
        if (article == null) return ResponseEntity.notFound().build();
        if (!article.getAuthor().getId().equals(currentUser(auth).getId())) {
            return ResponseEntity.status(403).body("Not your article");
        }
        articleRepository.delete(article);
        return ResponseEntity.noContent().build();
    }
}