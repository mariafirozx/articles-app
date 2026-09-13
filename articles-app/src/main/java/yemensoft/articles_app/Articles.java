package yemensoft.articles_app;

import java.time.LocalDateTime;

import org.apache.catalina.User;
import org.hibernate.annotations.ManyToAny;
import org.springframework.cglib.core.Local;

import jakarta.persistence.*;



@Entity 
@Table(name = "Articles")
public class Articles {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private String description;

    @ManyToOne 
    @JoinColumn(name = "user_id")
    private User author;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected  void onCreate(){
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
     @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }



    public Articles(){}

    public Articles(String title,String content,  String description, User author){
         this.title = title;
        this.content = content;
        this.description = description;
        this.author = author;

    }


    public long getId(){return id;}
    public void setId(long id){this.id = id;}

    public String getTitle(){return title;}
    public void setTitle(String title){this.title = title;}

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public User getAuthor() { return author; }
    public void setAuthor(User author) { this.author = author; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }


    

}
