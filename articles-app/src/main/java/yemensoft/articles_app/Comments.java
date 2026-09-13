package yemensoft.articles_app;

import java.time.LocalDateTime;

import javax.xml.stream.events.Comment;

import jakarta.persistence.*;


@Entity 
@Table (name = "Comments")
public class Comments {


    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;
    
    @ManyToOne
    @JoinColumn(name = "article_id") //articles table
    private Articles article;
    
    @ManyToOne
    @JoinColumn(name = "user_id") //users table
    private Users author;

    private LocalDateTime createdAt;

    @PrePersist
    protected  void onCreate(){
        createdAt = LocalDateTime.now();
    }
    
    public Comments(){}

    public Comments(String text, Articles article, Users author){
        this.text = text;
        this.article = article;
        this.author = author;
    }


    //setters n getters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    
    public Articles getArticle() { return article; }
    public void setArticle(Articles article) { this.article = article; }
    
    public Users getAuthor() { return author; }
    public void setAuthor(Users author) { this.author = author; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
