package com.example.myapp1.controller;

import com.example.myapp1.service.ImageService;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
// @RestController
// @RequestMapping("/api/images")
// public class ImageController {

//     @Autowired
//     private Cloudinary cloudinary;

//     @PostMapping("/upload")
//     public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
//         try {
//             Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
//             // uploadResult chứa nhiều thông tin như url, public_id,...
//             return ResponseEntity.ok(uploadResult);
//         } catch (IOException e) {
//             return ResponseEntity.status(500).body("Upload thất bại: " + e.getMessage());
//         }
//     }
// }
@RestController
@RequestMapping("/api/images")
public class ImageController {

    // @Autowired
    // private Cloudinary cloudinary;
    @Autowired
    private ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body("File không được để trống");
        }

        try {
            Map<String, Object> result = imageService.uploadImage(file);
            return ResponseEntity.ok(result);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Upload thất bại: " + e.getMessage());
        }
    }
}
