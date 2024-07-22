package com.blog.blog.service.impl;


import com.blog.blog.entity.Post;
import com.blog.blog.exception.ResourceNotFoundException;
import com.blog.blog.paylod.PostDto;
import com.blog.blog.repository.PostRepository;
import com.blog.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
   private PostRepository postRepository;

    @Autowired

    private ModelMapper modelMapper;

    @Override
    public PostDto createPost(PostDto postDto) {
        Post newpost=mapTOEntity(postDto);
            Post updatedpost=postRepository.save(newpost);
            return  mapTODto(updatedpost);


    }

    @Override
    public List<PostDto> listAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

        //Sort sort = Sort.by(sortBy);
      Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending():
                Sort.by(sortBy).descending();

        Pageable pageable =PageRequest.of(pageNo,pageSize,sort);

        Page<Post> listofPosts = postRepository.findAll(pageable);

        List<Post> posts = listofPosts.getContent();

        List<PostDto> postDtos = posts.stream().map(x -> mapTODto(x)).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Post not found with id: " +id)

        );
        PostDto postDto = mapTODto(post);
        return postDto;
    }

    @Override
    public PostDto updatePost(long id, PostDto postDto) {
        Post post = postRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Post not found with id: " +id)
        );
        Post newPost = mapTOEntity(postDto);
        newPost.setId(id);

        Post updatedPost = postRepository.save(newPost);
        PostDto dto = mapTODto(updatedPost);

        return dto;
    }

    @Override
    public void deletePostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Post not found with id: " +id)
        );
        postRepository.deleteById(id);
    }


    private PostDto mapTODto(Post post) {
        PostDto dto = modelMapper.map(post, PostDto.class);

        return dto;

    }

    private Post mapTOEntity(PostDto postDto) {
        Post post = modelMapper.map(postDto, Post.class);
        return post;
    }
}

