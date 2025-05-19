// package com.example.myapp1.controller;

// import org.springframework.core.io.ByteArrayResource;
// import org.springframework.http.*;
// import org.springframework.util.LinkedMultiValueMap;
// import org.springframework.util.MultiValueMap;
// import org.springframework.web.bind.annotation.*;
// import org.springframework.web.client.HttpClientErrorException;
// import org.springframework.web.client.RestTemplate;
// import org.springframework.web.multipart.MultipartFile;
// import org.json.JSONObject;

// import java.util.HashMap;
// import java.util.Map;
// import java.util.Base64;
// import java.util.Collections;

// @RestController
// @RequestMapping("/imgur")
// public class ImgurController {

// private static final String CLIENT_ID = "0cf25d8952be36d";

// // @PostMapping("/upload")
// // public ResponseEntity<String> uploadImage(@RequestParam("file")
// MultipartFile
// // file) {
// // try {
// // String uploadUrl = "https://api.imgur.com/3/image";

// // // Tạo headers
// // HttpHeaders headers = new HttpHeaders();
// // headers.setContentType(MediaType.MULTIPART_FORM_DATA);
// // headers.set("Authorization", "Client-ID " + CLIENT_ID);

// // // Tạo resource từ file
// // ByteArrayResource imageAsResource = new ByteArrayResource(file.getBytes())
// {
// // @Override
// // public String getFilename() {
// // return file.getOriginalFilename();
// // }
// // };

// // // Tạo body
// // MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
// // body.add("image", imageAsResource);

// // HttpEntity<MultiValueMap<String, Object>> requestEntity = new
// // HttpEntity<>(body, headers);

// // // Gửi request
// // RestTemplate restTemplate = new RestTemplate();
// // ResponseEntity<String> response = restTemplate.exchange(
// // uploadUrl,
// // HttpMethod.POST,
// // requestEntity,
// // String.class);

// // // Lấy URL ảnh từ response JSON
// // JSONObject jsonResponse = new JSONObject(response.getBody());
// // String imageUrl = jsonResponse.getJSONObject("data").getString("link");

// // return ResponseEntity.ok("Ảnh đã được upload thành công: " + imageUrl);

// // } catch (Exception e) {
// // e.printStackTrace();
// // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
// // .body("Upload thất bại: " + e.getMessage());
// // }
// // }

// // @PostMapping("/upload")
// // public ResponseEntity<String> uploadImage(@RequestParam("file")
// MultipartFile
// // file) {
// // try {
// // String uploadUrl = "https://api.imgur.com/3/image";

// // HttpHeaders headers = new HttpHeaders();
// // headers.setContentType(MediaType.MULTIPART_FORM_DATA);
// // headers.set("Authorization", "Client-ID " + CLIENT_ID);

// // ByteArrayResource imageAsResource = new ByteArrayResource(file.getBytes())
// {
// // @Override
// // public String getFilename() {
// // return file.getOriginalFilename();
// // }
// // };

// // MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
// // body.add("image", imageAsResource);

// // HttpEntity<MultiValueMap<String, Object>> requestEntity = new
// // HttpEntity<>(body, headers);

// // RestTemplate restTemplate = new RestTemplate();
// // ResponseEntity<String> response = restTemplate.exchange(uploadUrl,
// // HttpMethod.POST, requestEntity,
// // String.class);

// // if (response.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS) {
// // return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
// // .body("Bạn gửi quá nhiều yêu cầu. Vui lòng thử lại sau.");
// // }

// // JSONObject jsonResponse = new JSONObject(response.getBody());
// // String imageUrl = jsonResponse.getJSONObject("data").getString("link");
// // return ResponseEntity.ok("Ảnh đã được upload thành công: " + imageUrl);

// // } catch (HttpClientErrorException.TooManyRequests e) {
// // return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
// // .body("Bạn gửi quá nhiều yêu cầu. Vui lòng thử lại sau.");
// // } catch (Exception e) {
// // e.printStackTrace();
// // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
// // .body("Upload thất bại: " + e.getMessage());
// // }
// // }

// @PostMapping("/upload")
// public ResponseEntity<Map<String, Object>> uploadImage(@RequestParam("file")
// MultipartFile file) {
// Map<String, Object> response = new HashMap<>();
// try {
// String uploadUrl = "https://api.imgur.com/3/image";

// HttpHeaders headers = new HttpHeaders();
// headers.setContentType(MediaType.MULTIPART_FORM_DATA);
// headers.set("Authorization", "Client-ID " + CLIENT_ID);

// String base64Image = Base64.getEncoder().encodeToString(file.getBytes());

// MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
// body.add("image", base64Image);

// HttpEntity<MultiValueMap<String, String>> requestEntity = new
// HttpEntity<>(body, headers);

// RestTemplate restTemplate = new RestTemplate();
// ResponseEntity<String> responseEntity = restTemplate.exchange(uploadUrl,
// HttpMethod.POST, requestEntity,
// String.class);

// JSONObject jsonResponse = new JSONObject(responseEntity.getBody());
// String imageUrl = jsonResponse.getJSONObject("data").getString("link");

// response.put("message", "Ảnh đã được upload thành công");
// response.put("imageUrl", imageUrl);

// return ResponseEntity.ok(response);

// } catch (Exception e) {
// e.printStackTrace();
// response.put("message", "Upload thất bại");
// response.put("error", e.getMessage());
// return
// ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
// }
// }

// }

package com.example.myapp1.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Base64;
import java.util.Collections;

@RestController
@RequestMapping("/imgur")
public class ImgurController {

    private static final String CLIENT_ID = "0cf25d8952be36d";

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> uploadImage(
            @RequestPart("file") MultipartFile file) { // Thay @RequestParam bằng @RequestPart
        Map<String, Object> response = new HashMap<>();

        try {
            // Kiểm tra file có tồn tại không
            if (file.isEmpty()) {
                response.put("message", "File is empty");
                return ResponseEntity.badRequest().body(response);
            }

            // Kiểm tra kích thước file
            if (file.getSize() > 10 * 1024 * 1024) { // 10MB
                response.put("message", "File size exceeds 10MB limit");
                return ResponseEntity.badRequest().body(response);
            }

            String uploadUrl = "https://api.imgur.com/3/image";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            headers.set("Authorization", "Client-ID " + CLIENT_ID);

            // Tạo request body
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

            // Cách 1: Gửi dưới dạng base64
            String base64Image = Base64.getEncoder().encodeToString(file.getBytes());
            body.add("image", base64Image);

            // Hoặc cách 2: Gửi trực tiếp file
            /*
             * ByteArrayResource fileAsResource = new ByteArrayResource(file.getBytes()) {
             * 
             * @Override
             * public String getFilename() {
             * return file.getOriginalFilename();
             * }
             * };
             * body.add("image", fileAsResource);
             */

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    uploadUrl,
                    HttpMethod.POST,
                    requestEntity,
                    String.class);

            JSONObject jsonResponse = new JSONObject(responseEntity.getBody());
            String imageUrl = jsonResponse.getJSONObject("data").getString("link");

            response.put("success", true);
            response.put("message", "Ảnh đã được upload thành công");
            response.put("imageUrl", imageUrl);

            return ResponseEntity.ok(response);

        } catch (HttpClientErrorException e) {
            response.put("success", false);
            response.put("message", "Lỗi khi gửi đến Imgur");
            response.put("error", e.getResponseBodyAsString());
            return ResponseEntity.status(e.getStatusCode()).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Upload thất bại");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}