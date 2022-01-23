package com.pratap.blog.model.response;

import lombok.Data;

@Data
public class CommentResponseModel {

    private String commentId;
    private String name;
    private String email;
    private String body;
}
