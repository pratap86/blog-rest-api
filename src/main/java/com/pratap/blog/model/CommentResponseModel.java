package com.pratap.blog.model;

import lombok.Data;

@Data
public class CommentResponseModel {

    private String commentId;
    private String name;
    private String email;
    private String body;
}
