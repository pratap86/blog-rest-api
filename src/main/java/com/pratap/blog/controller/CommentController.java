package com.pratap.blog.controller;

import com.pratap.blog.dto.CommentDto;
import com.pratap.blog.model.request.CommentRequestModel;
import com.pratap.blog.model.response.CommentResponseModel;
import com.pratap.blog.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Api(value = "CRUD REST APIs for Comment Resources")
@RestController
@RequestMapping("/blog-api")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private ModelMapper modelMapper;

    @ApiOperation(value = "Create Comment REST API")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentResponseModel> createComment(@PathVariable("postId") String postId,
                                                              @Valid @RequestBody CommentRequestModel commentRequestModel) throws Exception {
        CommentDto commentDto = modelMapper.map(commentRequestModel, CommentDto.class);
        CommentDto serviceComment = commentService.createComment(postId, commentDto);
        return new ResponseEntity<>(modelMapper.map(serviceComment, CommentResponseModel.class), HttpStatus.OK);
    }

    @ApiOperation(value = "Get All Comments By Post ID REST API")
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentResponseModel>> getCommentsByPostId(@PathVariable("postId") String postId){

        List<CommentDto> comments = commentService.getCommentsByPostId(postId);

        return new ResponseEntity<>(comments.stream().map(commentDto ->
                modelMapper.map(commentDto, CommentResponseModel.class)).toList(), HttpStatus.FOUND);
    }

    @ApiOperation(value = "Get Single Comment By ID REST API")
    @GetMapping("posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentResponseModel> getCommentByPostIdAndCommentId(@PathVariable("postId") String postId,
                                                                               @PathVariable("commentId") String commentId){
        return new ResponseEntity<>(modelMapper.map(commentService.getCommentByPostIdAndCommentId(postId, commentId), CommentResponseModel.class),
                HttpStatus.FOUND);
    }

    @ApiOperation(value = "Update Comment By ID REST API")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentResponseModel> updateComment(@PathVariable("postId") String postId,
                                                              @PathVariable("commentId") String commentId,
                                                              @RequestBody CommentRequestModel commentRequestModel) throws Exception {

        return new ResponseEntity<>(modelMapper.map(commentService.updateComment(postId, commentId,
                modelMapper.map(commentRequestModel, CommentDto.class)), CommentResponseModel.class), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete Comment By ID REST API")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable("postId") String postId,
                                                @PathVariable("commentId") String commentId){

        commentService.deleteComment(postId, commentId);
        return new ResponseEntity<>("Comment deleted successful", HttpStatus.OK);
    }
}
