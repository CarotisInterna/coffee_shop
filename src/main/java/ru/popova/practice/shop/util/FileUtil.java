package ru.popova.practice.shop.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.FileAlreadyExistsException;

@Slf4j
public class FileUtil {

    private static final int BUFFER_SIZE = 16384;

    /**
     * чтение файла в выходной поток
     *
     * @param file     файл
     * @param response объект, формирующий ответ клиенту
     * @throws IOException
     */
    public static void readFile(File file, HttpServletResponse response) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(file);
             BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
             OutputStream outputStream = response.getOutputStream()) {
            int bytesRead;
            while ((bytesRead = bufferedInputStream.read()) != -1) {
                outputStream.write(bytesRead);
            }
        }
    }

    public static String saveFile(MultipartFile multipartFile, String name, String path) {
        try {
            log.info("Writing file with name \"{}\" in directory \"{}\" ", name, path);
            File directory = new File(path);
            if (!directory.exists()) {
                if (!directory.mkdir()) {
                    throw new IOException("cannot not create file");
                }
            }
            File file = new File(directory, name);
            boolean created = file.createNewFile();
            if (created) {
                try (FileOutputStream stream = new FileOutputStream(file.getPath())) {
                    byte[] buffer = new byte[BUFFER_SIZE];
                    InputStream str = multipartFile.getInputStream();
                    while (str.read(buffer) != -1) {
                        stream.write(buffer);
                    }
                }
                log.info("Successfully saved file with name \"{}\" in directory \"{}\" ", name, path);
                return name;
            } else {
                String errorMessage = String.format("File with name \"%s\" already exists in directory \"%s\"", name, path);
                log.error(errorMessage);
                throw new FileAlreadyExistsException(errorMessage);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
