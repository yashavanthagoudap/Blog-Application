package com.blog.blog.service.impl;

import com.blog.blog.entity.Comment;
import com.blog.blog.entity.Post;
import com.blog.blog.exception.ResourceNotFoundException;
import com.blog.blog.paylod.CommentDto;
import com.blog.blog.paylod.PostDto;
import com.blog.blog.repository.CommentRepository;
import com.blog.blog.repository.PostRepository;
import com.blog.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CommentServiceImpl implements CommentService {
@Autowired
    private CommentRepository commentRepository;
@Autowired
    private PostRepository postRepository;

    @Autowired

    private ModelMapper modelMapper;


    @Override
    public CommentDto createComment(long postId, CommentDto CommentDto) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post not found with id: " + postId)

        );
        Comment comment = mapTOEntity(CommentDto);

        comment.setPost(post);

        Comment newComment = commentRepository.save(comment);

        CommentDto dto = mapTODto(newComment);
        return dto;
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post not found with id: " + postId)

        );

        List<Comment> comments = commentRepository.findByPostId(postId);

        return comments.stream().map(comment -> mapTODto(comment)).collect(Collectors.toList());

    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post not found with id: " + postId)

        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment not found with id :" + commentId)
        );
        return mapTODto(comment);

    }

    @Override
    public CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post not found with id: " + postId)

        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment not found with id :" + commentId)
        );
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        Comment updatedComment = commentRepository.save(comment);

        return mapTODto(updatedComment);

    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post not found with id: " + postId)

        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment not found with id :" + commentId)
        );
        commentRepository.deleteById(commentId);

    }

    private CommentDto mapTODto(Comment comment) {
        CommentDto dto = modelMapper.map(comment, CommentDto.class);

        return dto;

    }

    private Comment mapTOEntity(CommentDto commentDto) {
        Comment comment = modelMapper.map(commentDto, Comment.class);
        return comment;
    }


}
