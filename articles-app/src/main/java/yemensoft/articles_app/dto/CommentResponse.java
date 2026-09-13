package yemensoft.articles_app.dto;
import java.util.*;
import java.time.LocalDateTime;

public class CommentResponse {

    private Long id;
    private String text;
    private Long articleId;
    private Long authorId;
    private String authorUsername;
    private LocalDateTime createdAt;

    public CommentResponse(Long id, String text, Long articleId, Long authorId,
                            String authorUsername, LocalDateTime createdAt) {
        this.id = id;
        this.text = text;
        this.articleId = articleId;
        this.authorId = authorId;
        this.authorUsername = authorUsername;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public String getText() { return text; }
    public Long getArticleId() { return articleId; }
    public Long getAuthorId() { return authorId; }
    public String getAuthorUsername() { return authorUsername; }
    public LocalDateTime getCreatedAt() { return createdAt; }

}
