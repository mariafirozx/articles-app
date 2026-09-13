package yemensoft.articles_app.dto;
import java.time.LocalDateTime;

public class ArticleResponse {
     private Long id;
    private String title;
    private String content;
    private String description;
    private String status;
    private Long authorId;
    private String authorUsername;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ArticleResponse(Long id, String title, String content, String description, String status,
                            Long authorId, String authorUsername,
                            LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.description = description;
        this.status = status;
        this.authorId = authorId;
        this.authorUsername = authorUsername;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getDescription() { return description; }
    public String getStatus() { return status; }
    public Long getAuthorId() { return authorId; }
    public String getAuthorUsername() { return authorUsername; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }


}
