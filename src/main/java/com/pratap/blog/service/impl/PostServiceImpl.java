package com.pratap.blog.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pratap.blog.dto.PostDto;
import com.pratap.blog.entity.Post;
import com.pratap.blog.exception.ResourceNotFoundException;
import com.pratap.blog.model.response.PostPageableResponseModel;
import com.pratap.blog.model.response.PostResponseModel;
import com.pratap.blog.repository.PostRepository;
import com.pratap.blog.service.PostService;
import com.pratap.blog.utils.IdGeneratorUtil;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public PostPageableResponseModel getPosts(int pageNo, int pageSize, String sortBy, String sortDir) throws Exception {
        log.info("Executing getPosts()...");

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> page = postRepository.findAll(pageRequest);

        // Get content from page
        List<Post> pageContent = page.getContent();

        List<PostResponseModel> postResponseModels = pageContent.stream().map(post -> modelMapper.map(post, PostResponseModel.class)).toList();
        PostPageableResponseModel postPageableResponseModel = new PostPageableResponseModel();
        postPageableResponseModel.setPostResponseModels(postResponseModels);

        postPageableResponseModel.setPageNo(page.getNumber());
        postPageableResponseModel.setPageSize(page.getSize());
        postPageableResponseModel.setTotalElements(page.getTotalElements());
        postPageableResponseModel.setTotalPages(page.getTotalPages());
        postPageableResponseModel.setLast(page.isLast());
        log.info("Fetched all posts={}", jsonMapper.writeValueAsString(postPageableResponseModel));
        return postPageableResponseModel;
    }

    @Override
    public PostDto getPostByPostId(String id) throws JsonProcessingException {
        log.info("Executing getPostById() by id={}", id);

        Post post = postRepository.findByPostId(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        log.info("Fetched Post record Post={}", jsonMapper.writeValueAsString(post));
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public PostDto updatePostByPostId(PostDto postDto, String postId) throws Exception {

        log.info("Executing updatePostByPostId() with payLoad={} and id={}", jsonMapper.writeValueAsString(postDto), postId);

        Post post = postRepository.findByPostId(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        if (postDto.getTitle() != null)
            post.setTitle(postDto.getTitle());
        if (postDto.getDescription() != null)
            post.setDescription(postDto.getDescription());
        if (postDto.getContent() != null)
            post.setContent(postDto.getContent());

        Post updatedPost = postRepository.save(post);
        return modelMapper.map(updatedPost, PostDto.class);
    }

    @Override
    public void deletePostByPostId(String postId) throws Exception {
        log.info("Executing deletePostByPostId() by postId={}", postId);
        Post post = postRepository.findByPostId(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
        log.info("Post fetched by postId={} and fetched Entity post={}", postId,
                jsonMapper.writeValueAsString(post));
        postRepository.delete(post);
    }
}
