package com.pratap.blog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pratap.blog.constant.AppConstant;
import com.pratap.blog.dto.PostDto;
import com.pratap.blog.model.response.PostPageableResponseModel;
import com.pratap.blog.model.request.PostRequestModel;
import com.pratap.blog.model.response.PostResponseModel;
import com.pratap.blog.service.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(value = "CRUD Rest APIs for Post Resources")
@RestController
@Slf4j
@RequestMapping("/blog-api")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper jsonMapper;

    @ApiOperation(value = "Create Post REST API")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/posts")
    public ResponseEntity<PostResponseModel> createPost(@Valid @RequestBody PostRequestModel postRequestModel) throws Exception {

        log.info("Executing createPost() with payload={}", jsonMapper.writeValueAsString(postRequestModel));
        PostDto postDto = modelMapper.map(postRequestModel, PostDto.class);
        PostDto savedPostDto = postService.createPost(postDto);
        return new ResponseEntity<>(modelMapper.map(savedPostDto, PostResponseModel.class), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Get All Posts REST API")
    @GetMapping("/posts")
    public ResponseEntity<PostPageableResponseModel> getAllPost(@RequestParam(value = "pageNo", defaultValue = AppConstant.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                                                @RequestParam(value = "pageSize", defaultValue = AppConstant.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                                                @RequestParam(value = "sortBy", defaultValue = AppConstant.DEFAULT_SORT_BY, required = false) String sortBy,
                                                                @RequestParam(value = "sortDir", defaultValue = AppConstant.DEFAULT_SORT_DIRECTION, required = false) String sortDir) throws Exception {

        log.info("Executing getAllPost()...");
        return new ResponseEntity<>(postService.getPosts(pageNo, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    @ApiOperation(value = "Get Post By Id REST API")
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostResponseModel> getPostByPostId(@PathVariable("postId") String postId) throws Exception {
        log.info("Executing getPostById() by id={}", postId);
        PostDto postDto = postService.getPostByPostId(postId);
        return new ResponseEntity<>(modelMapper.map(postDto, PostResponseModel.class), HttpStatus.FOUND);
    }

    @ApiOperation(value = "Update Post By Id REST API")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostResponseModel> updatePostByPostId(@RequestBody PostRequestModel postRequestModel, @PathVariable("postId") String postId) throws Exception {
        log.info("Executing updatePostByPostId() by Payload={} and postId={}",
                jsonMapper.writeValueAsString(postRequestModel), postId);

        PostDto postDto = postService.updatePostByPostId(modelMapper.map(postRequestModel, PostDto.class), postId);
        return new ResponseEntity<>(modelMapper.map(postDto, PostResponseModel.class), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete Post By Id REST API")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<String> deletePostByPostId(@PathVariable("postId") String postId) throws Exception {
        postService.deletePostByPostId(postId);
        return new ResponseEntity<>("Post entity deleted successfully", HttpStatus.OK);
    }
}