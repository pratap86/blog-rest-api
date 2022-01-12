package com.pratap.blog.service;

import com.pratap.blog.dto.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto createComment(String postId, CommentDto commentDto) throws Exception;

    List<CommentDto> getCommentsByPostId(String postId);

    CommentDto getCommentByPostIdAndCommentId(String postId, String commentId);

    CommentDto updateComment(String postId,String commentId, CommentDto commentDto) throws Exception;

    void deleteComment(String postId, String commentId);
}
