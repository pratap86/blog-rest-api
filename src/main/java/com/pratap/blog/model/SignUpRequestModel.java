package com.pratap.blog.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SignUpRequestModel {

    @NotEmpty
    @Size(min = 3, message = "SignUpRequest name should have at least 3 characters")
    private String name;

    @NotEmpty
    @Size(min = 5, message = "SignUpRequest username should have at least 3 characters")
    private String username;

    @NotEmpty(message = "role should be Admin or User")
    private String role;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String password;
}
