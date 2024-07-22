package com.blog.blog.repository;

import com.blog.blog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    //select * from Comment where post_id=2;
    List<Comment> findByPostId(long id);

}
