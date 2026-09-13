package yemensoft.articles_app.dto;

public class CommentRequest {
     private String text;
    private Long articleId;

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public Long getArticleId() { return articleId; }
    public void setArticleId(Long articleId) { this.articleId = articleId; }

}
