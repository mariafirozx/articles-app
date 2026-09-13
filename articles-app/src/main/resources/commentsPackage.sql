CREATE PACKAGE comment_pkg AS
    -- for comment, first valid if article is published
    PROCEDURE add_comment(
        p_text IN VARCHAR2,
        p_article_id IN NUMBER,
        p_user_id IN NUMBER,
        p_result OUT NUMBER
    );
    
    -- get comments 
    FUNCTION get_article_comments(p_article_id NUMBER) RETURN SYS_REFCURSOR;
END comment_pkg;
/

CREATE PACKAGE BODY comment_pkg AS
    
    PROCEDURE add_comment(
        p_text IN VARCHAR2,
        p_article_id IN NUMBER,
        p_user_id IN NUMBER,
        p_result OUT NUMBER
    ) AS
        v_status VARCHAR2(20);
    BEGIN
        -- article = published ?
        SELECT status INTO v_status FROM articles WHERE id = p_article_id;
        
        IF v_status = 'PUBLISHED' THEN
            INSERT INTO comments (text, article_id, user_id, created_at)
            VALUES (p_text, p_article_id, p_user_id, SYSDATE);
            p_result := 1; -- Success
        ELSE
            p_result := 0; -- Article not published, can't comment
        END IF;
    EXCEPTION
        WHEN OTHERS THEN
            p_result := -1;
    END;
    
    FUNCTION get_article_comments(p_article_id NUMBER) RETURN SYS_REFCURSOR AS
        v_cursor SYS_REFCURSOR;
    BEGIN
        OPEN v_cursor FOR
            SELECT * FROM comments WHERE article_id = p_article_id
            ORDER BY created_at DESC;
        RETURN v_cursor;
    END;
    
END comment_pkg;
/