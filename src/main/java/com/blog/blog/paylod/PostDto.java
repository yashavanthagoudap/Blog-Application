package com.blog.blog.paylod;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class PostDto {

    private long id;

    @NotEmpty
    @Size(min = 2,message = "Tittle should be more than 2 characters")
    private String tittle;

    @NotEmpty
    @Size(min = 5,message = "description should be min 5 characters")
    private String description;
    private String content;



}
