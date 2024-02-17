package com.example.imagecaptionbackend.service;

import com.example.imagecaptionbackend.entity.Image;
import com.example.imagecaptionbackend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.example.imagecaptionbackend.repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public User registerUser(String username, String password) {
        User user = new User(username, password);
        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Username already exists");
        }
    }

    public void login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("Username does not exist");
        }
        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Password is incorrect");
        }
    }

    public List<Image> getImages(Long userId){
        User user = userRepository.findById(userId).orElse(null);
        if(user == null){
            throw new IllegalArgumentException("User does not exist");
        }
        return user.getImages();
    }

}
