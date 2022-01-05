package com.pratap.blog.model;

import lombok.Data;

@Data
public class PostRequestModel {

    private String title;
    private String description;
    private String content;
}
