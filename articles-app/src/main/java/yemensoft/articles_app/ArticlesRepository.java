package yemensoft.articles_app;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;


public interface ArticlesRepository extends  JpaRepository<Articles, Long>{
    List<Articles> findByAuthorId(Long userId);
    List<Articles> findByTitleContainingIgnoreCase(String title);

}
