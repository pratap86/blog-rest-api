package com.pratap.blog.dto;

import lombok.Data;

@Data
public class CommentDto {

    private Long id;
    private String commentId;
    private String name;
    private String email;
    private String body;
}
