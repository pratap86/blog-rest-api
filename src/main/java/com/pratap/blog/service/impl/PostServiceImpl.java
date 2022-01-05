package com.pratap.blog.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pratap.blog.dto.PostDto;
import com.pratap.blog.entity.Post;
import com.pratap.blog.repository.PostRepository;
import com.pratap.blog.service.PostService;
import com.pratap.blog.utils.IdGeneratorUtil;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper jsonMapper;

    @Override
    public PostDto createPost(PostDto postDto) throws JsonProcessingException {
        postDto.setPostId(IdGeneratorUtil.generateId(6));
        log.info("Executing createPost() with payload={}", jsonMapper.writeValueAsString(postDto));
        Post postEntity = modelMapper.map(postDto, Post.class);
        Post savedEntity = postRepository.save(postEntity);
        return modelMapper.map(savedEntity, PostDto.class);
    }

    @Override
    public List<PostDto> getPosts() throws Exception {
        log.info("Executing getPosts()...");

        List<Post> savedPosts = postRepository.findAll();
        log.info("Fetched all posts={}", jsonMapper.writeValueAsString(savedPosts));
        return savedPosts.stream().map(post -> modelMapper.map(post, PostDto.class)).toList();
    }
}
