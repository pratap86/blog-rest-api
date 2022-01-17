package com.pratap.blog.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class LoginRequestModel {

    @NotEmpty(message = "usernameOrEmail should be valid")
    private String usernameOrEmail;

    @NotEmpty(message = "password should be valid")
    private String password;
}
