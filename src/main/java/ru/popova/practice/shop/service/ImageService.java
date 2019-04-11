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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static ru.popova.practice.shop.util.FileUtil.saveFile;
import static ru.popova.practice.shop.util.constants.PathConstants.IMAGE_NAME;
import static ru.popova.practice.shop.util.constants.PathConstants.PLACEHOLDER;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImagesConfig imagesConfig;
    private final DrinkImageRepository imageRepository;

    public List<DrinkImageEntity> saveImages(List<String> imagePaths, DrinkEntity drink) {

        AtomicInteger i = new AtomicInteger(0);

        List<DrinkImageEntity> images = imagePaths.stream()
                .peek(imagePath -> {
                    String fullPath = imagesConfig.getTmpPath() + imagePath;
                    File source = new File(fullPath);
                    File dest =
                            getFile(buildImageName(drink.getId(), i.incrementAndGet()));
                    try {
                        if (dest.createNewFile()) {
                            copyFile(source, dest);
                            removeFile(source);
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .map(imagePath -> new DrinkImageEntity(imagePath, drink))
                .collect(Collectors.toList());

        return imageRepository.saveAll(images);
    }

    private String buildImageName(Integer id, int num) {
        return IMAGE_NAME + PLACEHOLDER + id + PLACEHOLDER + num + imagesConfig.getSuffix();
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

    public void removeFile(File file) {
        if (file.delete()) {
            log.info("Successfully deleted image file {}", file);
        } else {
            //TODO: throw informative exception
            throw new RuntimeException("Cannot remove file " + file);
        }
    }

    public String saveTemporaryImage(MultipartFile image) {
        return saveFile(image, image.getOriginalFilename(), imagesConfig.getTmpPath());
    }

    public void copyFile(File source, File dest) {
        try (FileChannel sourceChannel = new FileInputStream(source).getChannel();
             FileChannel destChannel = new FileOutputStream(dest).getChannel()) {
            destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
