package com.blog.blog.controller;

import com.blog.blog.paylod.PostDto;
import com.blog.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
@Autowired
private PostService postService;
//http://localhost:9091/api/posts
@PreAuthorize("hasRole('ADMIN')")
@PostMapping
    public ResponseEntity<?> createPost(@Valid @RequestBody PostDto postDto, BindingResult result){

        if(result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        PostDto dto = postService.createPost(postDto);
        return new ResponseEntity<PostDto>(dto, HttpStatus.CREATED);
    }
    //http://localhost:9091/api/posts?pageNo=0&pageSize=5&sortBy=content&sortDir=asc/desc
    @GetMapping
    public List<PostDto> listAllPosts(
            @RequestParam(value = "pageNo",defaultValue = "0",required=false) int pageNo,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "id",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir
    ){
        List<PostDto> postDtos = postService.listAllPosts(pageNo,pageSize,sortBy,sortDir);
   return postDtos;
    }
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") long id){
        PostDto dto = postService.getPostById(id);

        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(
            @RequestBody PostDto postDto,
            @PathVariable("id") long id
    ){
        PostDto dto = postService.updatePost(id, postDto);
    return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deletePostById(@PathVariable("id")long id){
    postService.deletePostById(id);
   return new ResponseEntity<>("Post is deletes",HttpStatus.OK);
    }
}
