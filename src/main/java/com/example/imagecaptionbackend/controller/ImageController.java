package com.example.imagecaptionbackend.controller;

import com.example.imagecaptionbackend.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/images")
public class ImageController {
    @Autowired
    private ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(String name, byte[] data, Long userId) {
        try {
            imageService.createImage(name, data, userId);
            return ResponseEntity.ok("Upload successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteImage(Long id) {
        try {
            imageService.deleteImage(id);
            return ResponseEntity.ok("Delete successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/caption")
    public ResponseEntity<?> captionImage(int mode, Long id) {
        try {
            imageService.captionImage(mode, id);
            return ResponseEntity.ok(imageService.getLabel(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



}
