package ru.popova.practice.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.popova.practice.shop.config.ImagesConfig;
import ru.popova.practice.shop.util.FileUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImagesConfig imagesConfig;

    public void readFileIntoResponse(String fileName, HttpServletResponse response) {
        try {
            File file = getFile(fileName);
            response.setStatus(HttpStatus.OK.value());
            if (!file.exists()) {
                file = getPlaceholderImage();
            }
            response.setHeader(HttpHeaders.CONTENT_TYPE, "image/jpeg");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + file.getName() + ".jpg\"");
            response.setHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(file.length()));
            FileUtil.readFile(file, response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private File getPlaceholderImage() {
        return new File(imagesConfig.getPath() + "default" + imagesConfig.getSuffix());
    }

    private File getFile(String fileName) {
        return new File(imagesConfig.getPath() + fileName + imagesConfig.getSuffix());
    }
}
