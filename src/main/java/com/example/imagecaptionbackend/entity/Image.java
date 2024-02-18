package com.example.imagecaptionbackend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Lob
    @Column(name = "data")
    private byte[] data;
    @Column(name = "label_1")
    private String label_1;

    public Image(byte[] data) {
        this.data = data;
    }

    public Image() {
    }

    public Long getId() {
        return id;
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

    public boolean isCaptioned() {
        return label_1 != null && !label_1.isEmpty();
    }
}
