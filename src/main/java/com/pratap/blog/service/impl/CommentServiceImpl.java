package com.pratap.blog.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pratap.blog.dto.CommentDto;
import com.pratap.blog.entity.Comment;
import com.pratap.blog.entity.Post;
import com.pratap.blog.exception.ResourceNotFoundException;
import com.pratap.blog.repository.CommentRepository;
import com.pratap.blog.repository.PostRepository;
import com.pratap.blog.service.CommentService;
import com.pratap.blog.utils.IdGeneratorUtil;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ObjectMapper jsonMapper;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(String postId, CommentDto commentDto) throws JsonProcessingException {

        log.info("Executing createComment() with postId={} & payload={}", postId, jsonMapper.writeValueAsString(commentDto));
        Post post = postRepository.findByPostId(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));

        Comment comment = modelMapper.map(commentDto, Comment.class);
        comment.setCommentId(IdGeneratorUtil.generateId(10));
        comment.setPost(post);
        return modelMapper.map(commentRepository.save(comment), CommentDto.class);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(String postId) {

        log.info("Executing getCommentsByPostId() with postId={}", postId);
        List<Comment> comments = commentRepository.fetchCommentsByPostId(postId);
        if (comments.size() == 0)
            throw new ResourceNotFoundException("Comment", "postId", postId);
        return comments.stream().map(comment -> modelMapper.map(comment, CommentDto.class)).toList();
    }

    @Override
    public CommentDto getCommentByPostIdAndCommentId(String postId, String commentId) {

        log.info("Executing getCommentByPostIdAndCommentId() with postId={} and commentId={}", postId,
                commentId);
        isIdsAreValid(postId, commentId);
        Comment fetchedComment = commentRepository.fetchCommentByPostIdAndCommentId(postId, commentId);
        return modelMapper.map(fetchedComment, CommentDto.class);
    }

    private void isIdsAreValid(String postId, String commentId) {
        postRepository.findByPostId(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post", "postId", postId));
        commentRepository.findByCommentId(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "commentId", commentId));
    }

    @Override
    public CommentDto updateComment(String postId, String commentId, CommentDto commentDto) throws JsonProcessingException {

        log.info("Executing updateComment() with postId={}, commentId={} and payload={}",
                postId, commentId, jsonMapper.writeValueAsString(commentDto));

        isIdsAreValid(postId, commentId);
        Comment comment = commentRepository.fetchCommentByPostIdAndCommentId(postId, commentId);

        if (commentDto.getName() != null)
            comment.setName(commentDto.getName());
        if (commentDto.getEmail() != null)
            comment.setEmail(commentDto.getEmail());
        if (commentDto.getBody() != null)
            comment.setBody(commentDto.getBody());

        Comment updatedComment = commentRepository.save(comment);
        return modelMapper.map(updatedComment, CommentDto.class);
    }

    @Override
    public void deleteComment(String postId, String commentId) {

        log.info("Executing deleteComment() by postId={} and commentId={}", postId, commentId);
        isIdsAreValid(postId, commentId);
        Comment comment = commentRepository.fetchCommentByPostIdAndCommentId(postId, commentId);
        commentRepository.delete(comment);
    }
}
