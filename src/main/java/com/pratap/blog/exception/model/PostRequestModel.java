package com.pratap.blog.exception.model;

import lombok.Data;

@Data
public class PostRequestModel {

    private String title;
    private String description;
    private String content;
}
