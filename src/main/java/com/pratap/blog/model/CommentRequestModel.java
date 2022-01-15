package com.pratap.blog.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class CommentRequestModel {

    @NotEmpty
    @Size(min = 3, message = "Comment name should have at least 3 characters")
    private String name;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @Size(min = 10, message = "Comment body should have at least 10 characters")
    private String body;
}
