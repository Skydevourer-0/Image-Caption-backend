package com.example.imagecaptionbackend.controller;

import com.example.imagecaptionbackend.service.ImageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/images")
public class ImageController {
    @Autowired
    private ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(byte[] data, HttpServletRequest request) {
        Long userId = (Long) request.getSession().getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.badRequest().body("Please login first");
        }
        try {
            imageService.createImage(userId, data);
            return ResponseEntity.ok("Upload successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteImage(Long id, HttpServletRequest request) {
        String role = (String) request.getSession().getAttribute("role");
        if (role == null) {
            return ResponseEntity.badRequest().body("Please login first");
        }
        try {
            imageService.deleteImage(role, id);
            return ResponseEntity.ok("Delete successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/one")
    public ResponseEntity<?> getImage(Long id) {
        try {
            return ResponseEntity.ok(imageService.getImage(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserImages(int page, int size, HttpServletRequest request) {
        Long userId = (Long) request.getSession().getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.badRequest().body("Please login first");
        }
        try {
            return ResponseEntity.ok(imageService.getUserImages(userId, page, size));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/admin")
    public ResponseEntity<?> getAllImages(Long userId, int page, int size, HttpServletRequest request) {
        String role = (String) request.getSession().getAttribute("role");
        if (role == null) {
            return ResponseEntity.badRequest().body("Please login first");
        } else if (!role.equals("admin")) {
            return ResponseEntity.badRequest().body("You are not an admin");
        }
        try {
            return ResponseEntity.ok(imageService.getUserImages(userId, page, size));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllImages(int page, int size) {
        try {
            return ResponseEntity.ok(imageService.getAllImages(page, size));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/caption")
    public ResponseEntity<?> captionImage(int mode, Long id) {
        try {
            String label = imageService.captionImage(mode, id);
            return ResponseEntity.ok(label);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
