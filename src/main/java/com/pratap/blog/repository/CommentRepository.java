package com.pratap.blog.repository;

import com.pratap.blog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, String> {

    @Query(value = "SELECT * FROM comments WHERE post_id = ?1",
    nativeQuery = true)
    List<Comment> fetchCommentsByPostId(String postId);

    @Query(value = "SELECT * FROM comments WHERE post_id = ?1 and comment_id = ?2",
            nativeQuery = true)
    Comment fetchCommentByPostIdAndCommentId(String postId, String commentId);

    Optional<Comment> findByCommentId(String commentId);
}
