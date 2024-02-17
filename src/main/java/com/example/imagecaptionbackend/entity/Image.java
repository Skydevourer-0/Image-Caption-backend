package com.example.imagecaptionbackend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Lob
    @Column(name = "data")
    private byte[] data;
    @Column(name = "label_1")
    private String label_1;

    // TODO:维护图片-用户的多对一关系
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Image(String name, byte[] data, User user) {
        this.name = name;
        this.data = data;
        this.user = user;
    }

    public Image() {
    }

    public byte[] getData() {
        return data;
    }

    public String getLabel_1() {
        return label_1;
    }

    public void setLabel_1(String label) {
        this.label_1 = label;
    }
}
