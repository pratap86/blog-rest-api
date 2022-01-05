package com.pratap.blog.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pratap.blog.dto.PostDto;
import com.pratap.blog.model.PostRequestModel;
import com.pratap.blog.model.PostResponseModel;
import com.pratap.blog.service.PostService;
import lombok.extern.slf4j.Slf4j;
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

import java.util.List;

@RestController
@Slf4j
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper jsonMapper;

    @PostMapping("/posts")
    public ResponseEntity<PostResponseModel> createPost(@RequestBody PostRequestModel postRequestModel) throws Exception {

        log.info("Executing createPost() with payload={}", jsonMapper.writeValueAsString(postRequestModel));
        PostDto postDto = modelMapper.map(postRequestModel, PostDto.class);
        PostDto savedPostDto = postService.createPost(postDto);
        return new ResponseEntity<>(modelMapper.map(savedPostDto, PostResponseModel.class), HttpStatus.CREATED);
    }

    @GetMapping("/posts")
    public ResponseEntity<List<PostResponseModel>> getAllPost() throws Exception{

        log.info("Executing getAllPost()...");
        List<PostDto> postsDto = postService.getPosts();
        return new ResponseEntity<>(postsDto.stream().map(postDto -> modelMapper.map(postDto, PostResponseModel.class)).toList(), HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostResponseModel> getPostByPostId(@PathVariable("postId") String postId) throws Exception {
        log.info("Executing getPostById() by id={}", postId);
        PostDto postDto = postService.getPostByPostId(postId);
        return new ResponseEntity<>(modelMapper.map(postDto, PostResponseModel.class), HttpStatus.FOUND);
    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostResponseModel> updatePostByPostId(@RequestBody PostRequestModel postRequestModel, @PathVariable("postId") String postId) throws Exception {
        log.info("Executing updatePostByPostId() by Payload={} and postId={}",
                jsonMapper.writeValueAsString(postRequestModel), postId);

        PostDto postDto = postService.updatePostByPostId(modelMapper.map(postRequestModel, PostDto.class), postId);
        return new ResponseEntity<>(modelMapper.map(postDto, PostResponseModel.class), HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<String> deletePostByPostId(@PathVariable("postId") String postId) throws Exception {
        postService.deletePostByPostId(postId);
        return new ResponseEntity<>("Post entity deleted successfully", HttpStatus.OK);
    }
}