CREATE PACKAGE article_pkg AS
    --  ID
    FUNCTION generate_article_id RETURN NUMBER;
    
    -- new article
    PROCEDURE create_article(
        p_title IN VARCHAR2,
        p_content IN VARCHAR2,
        p_description IN VARCHAR2,
        p_user_id IN NUMBER,
        p_article_id OUT NUMBER
    );
    
    -- user publish article
    PROCEDURE publish_article(
        p_article_id IN NUMBER,
        p_result OUT NUMBER
    );
    
    -- getw published articles only
    FUNCTION get_published_articles RETURN SYS_REFCURSOR;
    
    -- search published article
    FUNCTION search_published_articles(p_title VARCHAR2) RETURN SYS_REFCURSOR;
END article_pkg;
/

CREATE PACKAGE BODY article_pkg AS
    
    FUNCTION generate_article_id RETURN NUMBER IS
    BEGIN
        RETURN article_seq.NEXTVAL;
    END;
    
    PROCEDURE create_article(
        p_title IN VARCHAR2,
        p_content IN VARCHAR2,
        p_description IN VARCHAR2,
        p_user_id IN NUMBER,
        p_article_id OUT NUMBER
    ) AS
    BEGIN
        p_article_id := generate_article_id();
        INSERT INTO articles (id, title, content, description, user_id, status, created_at)
        VALUES (p_article_id, p_title, p_content, p_description, p_user_id, 'DRAFT', SYSDATE);
    EXCEPTION
        WHEN OTHERS THEN
            p_article_id := -1;
    END;
    
    PROCEDURE publish_article(p_article_id IN NUMBER, p_result OUT NUMBER) AS
    BEGIN
        UPDATE articles SET status = 'PUBLISHED', updated_at = SYSDATE
        WHERE id = p_article_id;
        p_result := 1;
    EXCEPTION
        WHEN OTHERS THEN
            p_result := 0;
    END;
    
    FUNCTION get_published_articles RETURN SYS_REFCURSOR AS
        v_cursor SYS_REFCURSOR;
    BEGIN
        OPEN v_cursor FOR
            SELECT * FROM articles WHERE status = 'PUBLISHED' ORDER BY created_at DESC;
        RETURN v_cursor;
    END;
    
    FUNCTION search_published_articles(p_title VARCHAR2) RETURN SYS_REFCURSOR AS
        v_cursor SYS_REFCURSOR;
    BEGIN
        OPEN v_cursor FOR
            SELECT * FROM articles 
            WHERE status = 'PUBLISHED' AND title LIKE '%' || p_title || '%'
            ORDER BY created_at DESC;
        RETURN v_cursor;
    END;
    
END article_pkg;
/