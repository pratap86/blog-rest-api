package com.pratap.blog.exception.model;

import lombok.Data;

@Data
public class PostResponseModel {

    private String postId;
    private String title;
    private String description;
    private String content;
}
