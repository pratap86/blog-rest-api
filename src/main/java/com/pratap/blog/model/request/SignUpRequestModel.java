package com.pratap.blog.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@ApiModel("SignUpRequestModel model information")
@Getter
@Setter
public class SignUpRequestModel {

    @ApiModelProperty(value = "SignUpRequestModel name")
    @NotEmpty
    @Size(min = 3, message = "SignUpRequest name should have at least 3 characters")
    private String name;

    @ApiModelProperty(value = "SignUpRequestModel username")
    @NotEmpty
    @Size(min = 5, message = "SignUpRequest username should have at least 3 characters")
    private String username;

    @ApiModelProperty(value = "SignUpRequestModel role")
    @NotEmpty(message = "role should be Admin or User")
    private String role;

    @ApiModelProperty(value = "SignUpRequestModel email")
    @NotEmpty
    @Email
    private String email;

    @ApiModelProperty(value = "SignUpRequestModel password")
    @NotEmpty
    private String password;
}
