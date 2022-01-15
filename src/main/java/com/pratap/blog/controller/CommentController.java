package com.pratap.blog.controller;

import com.pratap.blog.dto.CommentDto;
import com.pratap.blog.model.CommentRequestModel;
import com.pratap.blog.model.CommentResponseModel;
import com.pratap.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentResponseModel> createComment(@PathVariable("postId") String postId,
                                                              @Valid @RequestBody CommentRequestModel commentRequestModel) throws Exception {
        CommentDto commentDto = modelMapper.map(commentRequestModel, CommentDto.class);
        CommentDto serviceComment = commentService.createComment(postId, commentDto);
        return new ResponseEntity<>(modelMapper.map(serviceComment, CommentResponseModel.class), HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentResponseModel>> getCommentsByPostId(@PathVariable("postId") String postId){

        List<CommentDto> comments = commentService.getCommentsByPostId(postId);

        return new ResponseEntity<>(comments.stream().map(commentDto ->
                modelMapper.map(commentDto, CommentResponseModel.class)).toList(), HttpStatus.FOUND);
    }

    @GetMapping("posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentResponseModel> getCommentByPostIdAndCommentId(@PathVariable("postId") String postId,
                                                                               @PathVariable("commentId") String commentId){
        return new ResponseEntity<>(modelMapper.map(commentService.getCommentByPostIdAndCommentId(postId, commentId), CommentResponseModel.class),
                HttpStatus.FOUND);
    }

    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentResponseModel> updateComment(@PathVariable("postId") String postId,
                                                              @PathVariable("commentId") String commentId,
                                                              @RequestBody CommentRequestModel commentRequestModel) throws Exception {

        return new ResponseEntity<>(modelMapper.map(commentService.updateComment(postId, commentId,
                modelMapper.map(commentRequestModel, CommentDto.class)), CommentResponseModel.class), HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable("postId") String postId,
                                                @PathVariable("commentId") String commentId){

        commentService.deleteComment(postId, commentId);
        return new ResponseEntity<>("Comment deleted successful", HttpStatus.OK);
    }
}
