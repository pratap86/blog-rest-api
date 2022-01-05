package com.pratap.blog.service;

import com.pratap.blog.dto.PostDto;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto) throws Exception;

    List<PostDto> getPosts() throws Exception;

    PostDto getPostByPostId(String id) throws Exception;

    PostDto updatePostByPostId(PostDto postDto, String postId) throws Exception;

    void deletePostByPostId(String postId) throws Exception;
}
