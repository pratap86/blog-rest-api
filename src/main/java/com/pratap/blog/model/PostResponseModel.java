package com.pratap.blog.model;

import lombok.Data;

import java.util.Set;

@Data
public class PostResponseModel {

    private String postId;
    private String title;
    private String description;
    private String content;
    private Set<CommentResponseModel> comments;
}
