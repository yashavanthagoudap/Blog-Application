package com.blog.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "posts",
        uniqueConstraints = @UniqueConstraint(columnNames = {"tittle"})

)
public class Post {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
@Column(name = "tittle",nullable = false,unique = true)
    private String tittle;

    @Column(name = "description",nullable = false)
    private String description;

    @Column(name = "content",nullable = false)
    private String content;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }



    @OneToMany(cascade = CascadeType.ALL,mappedBy = "post")
    private List<Comment> comments;
}
