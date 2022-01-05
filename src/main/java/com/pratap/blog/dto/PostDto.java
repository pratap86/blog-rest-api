package com.pratap.blog.dto;

import lombok.Data;

@Data
public class PostDto {

    private Long id;
    private String postId;
    private String title;
    private String description;
    private String content;
}
