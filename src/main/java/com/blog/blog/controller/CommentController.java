package com.blog.blog.controller;

import com.blog.blog.paylod.CommentDto;
import com.blog.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {

    @Autowired
    private CommentService commentService;

    //http://localhost:9091/api/posts/1/comments

    @PostMapping("posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(
            @PathVariable(value = "postId")long postId,
            @RequestBody CommentDto commentDto) {

        return new ResponseEntity<>(commentService.createComment(postId,commentDto), HttpStatus.CREATED);

    }

    //http://localhost:9091/api/posts/2/comments
    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> getCommentsById(@PathVariable(value = "postId")long postId){
       return commentService.getCommentsByPostId(postId);
    }

    //http://localhost:9091/api/posts/2/comments/1
    @GetMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> getCommentById(
            @PathVariable(value = "postId")Long postId,
            @PathVariable(value = "id")Long commentId
    ){
        CommentDto commentDto = commentService.getCommentById(postId, commentId);
   return new ResponseEntity<>(commentDto,HttpStatus.OK);
    }

    //http://localhost:9091/api/posts/{posstId}/comments/{id}
    @PutMapping("posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> updateComment(
            @PathVariable(value = "postId")Long postId,
            @PathVariable(value = "id")Long commentId,
            @RequestBody CommentDto commentDto
    ){
        CommentDto dto = commentService.updateComment(postId, commentId, commentDto);

   return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    //http://localhost:9091/api/posts/{posstId}/comments/{id}
    @DeleteMapping("/posts/{postId}/comments/{id}")

    public ResponseEntity<String> deleteComment(
            @PathVariable(value = "postId")Long postId,
            @PathVariable(value = "id")Long commentId
    ){
        commentService.deleteComment(postId,commentId);

        return new ResponseEntity<>("Comment is deleted",HttpStatus.OK);
    }

}
