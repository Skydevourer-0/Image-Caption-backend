package com.example.imagecaptionbackend.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true,name = "username")
    private String username;
    @Column(name = "password")
    private String password;

    // TODO:维护用户-图片的一对多关系
    @OneToMany(targetEntity = Image.class, mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "images",referencedColumnName = "image_id")
    private List<Image> images;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User() {}

    public String getPassword() {
        return password;
    }

    public List<Image> getImages() {
        return images;
    }
}
