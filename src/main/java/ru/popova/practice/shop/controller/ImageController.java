package ru.popova.practice.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.popova.practice.shop.service.ImageService;

import javax.servlet.http.HttpServletResponse;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageController {

    public final ImageService imageService;

    @GetMapping("/{imageName}")
    public void getImage(@PathVariable String imageName, HttpServletResponse response) {
        imageService.readFileIntoResponse(imageName, response);
    }

    @PostMapping
    public ResponseEntity<?> saveTemporaryImage(@RequestParam("image") MultipartFile image) {
        return ok(imageService.saveTemporaryImage(image));
    }
}
