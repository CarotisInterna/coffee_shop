package ru.popova.practice.shop;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@Slf4j
public class Application implements WebMvcConfigurer {
    private static final Logger logger = LogManager.getLogger(Application.class);

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
