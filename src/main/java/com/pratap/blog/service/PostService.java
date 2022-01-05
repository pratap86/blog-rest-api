package com.pratap.blog.service;

import com.pratap.blog.dto.PostDto;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto) throws Exception;

    List<PostDto> getPosts() throws Exception;
}
