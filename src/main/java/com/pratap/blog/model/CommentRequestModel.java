package com.pratap.blog.model;

import lombok.Data;

@Data
public class CommentRequestModel {

    private String name;
    private String email;
    private String body;
}
