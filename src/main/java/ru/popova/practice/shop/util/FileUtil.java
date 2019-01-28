package ru.popova.practice.shop.util;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class FileUtil {

    /**
     * чтение файла в выходной поток
     * @param file файл
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
}
