package ru.popova.practice.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.popova.practice.shop.service.ImageService;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/images/")
@RequiredArgsConstructor
public class ImageController {

    public final ImageService imageService;

    @GetMapping("/{imageName}")
    public void getImage(@PathVariable String imageName, HttpServletResponse response) {
        imageService.readFileIntoResponse(imageName, response);
    }
}
