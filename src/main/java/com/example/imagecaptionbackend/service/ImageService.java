package com.example.imagecaptionbackend.service;

import com.example.imagecaptionbackend.entity.*;
import com.example.imagecaptionbackend.repository.ImageRepository;
import com.example.imagecaptionbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class ImageService {
    @Autowired
    public ImageRepository imageRepository;
    public UserRepository userRepository;

    public void createImage(String name, byte[] data, Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new IllegalArgumentException("User does not exist");
        }
        Image image = new Image(name, data, user);
        // TODO: 这样是否将图片插入到用户的图片列表中了？
        user.getImages().add(image);
        imageRepository.save(image);
    }

    public void deleteImage(Long id) {
        imageRepository.deleteById(id);
    }

    public void captionImage(int mode, Long id) {
        Image image = imageRepository.findById(id).orElse(null);
        if (image == null) {
            throw new IllegalArgumentException("Image does not exist");
        }
        byte[] data = image.getData();
        try {
            String pythonScriptPath = "src/main/python/caption.py";
            String[] cmd = {"python", pythonScriptPath};
            Process p = new ProcessBuilder(cmd).start();
            // 输入图片数据
            PrintWriter stdin = new PrintWriter(new OutputStreamWriter(p.getOutputStream(), StandardCharsets.UTF_8));
            stdin.println(Base64.getEncoder().encodeToString(data));
            stdin.close();
            // 读取输出
            BufferedReader stdout = new BufferedReader(new InputStreamReader(p.getInputStream(), StandardCharsets.UTF_8));
            String label = stdout.readLine();
            // 设置超时时间
            int delay = 5;
            if (!p.waitFor(delay, java.util.concurrent.TimeUnit.SECONDS)) {
                p.destroy();
                throw new InterruptedException("Timeout");
            }
            // 设置标签
            image.setLabel_1(label);
            imageRepository.save(image);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Caption failed");
        }
    }

    public String getLabel(Long id) {
        Image image = imageRepository.findById(id).orElse(null);
        if (image == null) {
            throw new IllegalArgumentException("Image does not exist");
        }
        return image.getLabel_1();
    }
}
