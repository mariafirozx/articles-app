package yemensoft.articles_app;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;


public interface ArticlesRepository extends  JpaRepository<Articles, Long>{
    List<Articles> findByAuthorIdOrderByCreatedAtDesc(Long authorId);

    List<Articles> findByAuthorIdAndStatusOrderByCreatedAtDesc(Long authorId, String status);

    @Query("SELECT a FROM Articles a WHERE a.status = 'PUBLISHED' " +
           "AND (:authorId IS NULL OR a.author.id = :authorId) " +
           "AND (:content IS NULL OR LOWER(a.title) LIKE LOWER(CONCAT('%', :content, '%')) " +
           "     OR LOWER(a.content) LIKE LOWER(CONCAT('%', :content, '%'))) " +
           "ORDER BY a.createdAt DESC")
    List<Articles> searchPublished(@Param("authorId") Long authorId, @Param("content") String content);

}
