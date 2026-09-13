package yemensoft.articles_app;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface CommentsRepository extends JpaRepository<Comments, Long>{
        List<Comments> findByArticleId(Long articleId);


}
