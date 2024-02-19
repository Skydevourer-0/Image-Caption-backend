package com.example.imagecaptionbackend.controller;

import com.example.imagecaptionbackend.entity.User;
import com.example.imagecaptionbackend.service.UserService;
import io.swagger.annotations.Api;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "用户管理")
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(HttpServletRequest request) {
        String username = (String) request.getSession().getAttribute("username");
        if (username == null) {
            return ResponseEntity.badRequest().body("Please login first");
        } else {
            return ResponseEntity.ok(username);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(String username, String password, int role) {
        try {
            User user = userService.registerUser(username, password, role);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(String username, String password, HttpServletRequest request) {
        try {
            User user = userService.login(username, password);
            request.getSession().setAttribute("userId", user.getId());
            request.getSession().setAttribute("username", username);
            request.getSession().setAttribute("role", user.getRole());
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        request.getSession().removeAttribute("userId");
        request.getSession().removeAttribute("username");
        request.getSession().removeAttribute("role");
        return ResponseEntity.ok("Logout successfully");
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateUser(String username, String password, HttpServletRequest request) {
        String curUsername = (String) request.getSession().getAttribute("username");
        if (curUsername == null) {
            return ResponseEntity.badRequest().body("Please login first");
        } else if (!curUsername.equals(username)) {
            return ResponseEntity.badRequest().body("You can only update your own profile");
        }
        try {
            userService.updateUser(username, password);
            return ResponseEntity.ok("Update successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteUser(String username, HttpServletRequest request) {
        String role = (String) request.getSession().getAttribute("role");
        if (role == null) {
            return ResponseEntity.badRequest().body("Please login first");
        }
        try {
            userService.deleteUser(role, username);
            return ResponseEntity.ok("Delete successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/one")
    public ResponseEntity<?> getUser(String username, HttpServletRequest request) {
        String role = (String) request.getSession().getAttribute("role");
        if (role == null) {
            return ResponseEntity.badRequest().body("Please login first");
        }
        try {
            return ResponseEntity.ok(userService.getUser(role, username));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers(int page, int size, HttpServletRequest request) {
        String role = (String) request.getSession().getAttribute("role");
        if (role == null) {
            return ResponseEntity.badRequest().body("Please login first");
        }
        try {
            return ResponseEntity.ok(userService.getAllUsers(role, page, size));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
