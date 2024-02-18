package com.example.imagecaptionbackend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_image")
public class UserImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "image_id")
    private Long imageId;

    public UserImage(Long userId, Long imageId) {
        this.userId = userId;
        this.imageId = imageId;
    }

    public UserImage() {
    }

    public Long getImageId(){
        return imageId;
    }
}
