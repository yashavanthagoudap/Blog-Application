package com.blog.blog.service;

import com.blog.blog.paylod.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto createComment(long postId,CommentDto CommentDto);

    List<CommentDto> getCommentsByPostId(long postId);

    CommentDto getCommentById(Long postId,Long commentId);

    CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto);


    void deleteComment(Long postId, Long commentId);
}
