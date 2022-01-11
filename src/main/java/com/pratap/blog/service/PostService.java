package com.pratap.blog.service;

import com.pratap.blog.dto.PostDto;
import com.pratap.blog.exception.model.PostPageableResponseModel;

public interface PostService {

    PostDto createPost(PostDto postDto) throws Exception;

    PostPageableResponseModel getPosts(int pageNo, int pageSize, String sortBy, String sortDir) throws Exception;

    PostDto getPostByPostId(String id) throws Exception;

    PostDto updatePostByPostId(PostDto postDto, String postId) throws Exception;

    void deletePostByPostId(String postId) throws Exception;
}
