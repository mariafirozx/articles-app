package yemensoft.articles_app;

import org.hibernate.boot.models.JpaAnnotations;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface UserRepository extends JpaRepository<Users, Long>{

     Optional<Users> findByUsername(String username);
    Optional<Users> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
} 
