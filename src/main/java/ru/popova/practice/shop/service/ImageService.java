package ru.popova.practice.shop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.popova.practice.shop.config.ImagesConfig;
import ru.popova.practice.shop.entity.DrinkEntity;
import ru.popova.practice.shop.entity.DrinkImageEntity;
import ru.popova.practice.shop.repository.DrinkImageRepository;
import ru.popova.practice.shop.util.FileUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static ru.popova.practice.shop.util.FileUtil.saveFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImagesConfig imagesConfig;
    private final DrinkImageRepository imageRepository;

    public List<DrinkImageEntity> saveImages(List<MultipartFile> images, DrinkEntity drink) {
        return images.stream()
                .map(multipartFile -> saveFile(multipartFile, multipartFile.getOriginalFilename(), imagesConfig.getPath()))
                .map(path -> new DrinkImageEntity(path, drink))
                .map(imageRepository::save)
                .collect(Collectors.toList());
    }

    public List<DrinkImageEntity> getImagesByDrinkId(Integer id) {
        return imageRepository.findAllByDrinkId(id);
    }


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

    public void removeImages(List<DrinkImageEntity> oldImages) {
        oldImages.forEach(image -> {
            String fileName = image.getImage();
            File file = getFile(fileName);
            if (file.delete()) {
                log.info("Successfully deleted image file {}", image);
                imageRepository.delete(image);
            } else {
                //TODO: throw informative exception
                throw new RuntimeException("Cannot remove image " + image);
            }
        });
    }
}
