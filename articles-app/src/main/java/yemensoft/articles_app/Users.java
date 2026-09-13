package yemensoft.articles_app;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity //user bd
@Table(name = "users")
public class Users {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String password;
    private String fullname;

    //empty constructor firts
    public Users(){}

    public Users(String username, String email, String password, String fullname){
        this.username = username;
        this.email = email;
        this.password = password;
        this.fullname = fullname;

    }

    //setters and getters
    public long getId(){return id;}
    public void setId(long id){this.id = id;}

    public String getUsername(){return  username;}
    public void setUsername(String username){this.username = username;}

    public String getEmail(){return email;}

    public void setEmail(String email){this.email = email;}

     public String getPassword(){return password;}
    public void setPassword(String password){this.password = password;}

     public String getFullname(){return fullname;}
    public void setFullname(String fullname){this.fullname = fullname;}



}
