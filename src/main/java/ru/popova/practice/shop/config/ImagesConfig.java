package ru.popova.practice.shop.config;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotBlank;

@Slf4j
@Getter
@Setter
@EnableConfigurationProperties
@Configuration
@ConfigurationProperties(prefix = "coffee-shop.images")
public class ImagesConfig {

    @NotBlank
    private String path;
    @NotBlank
    private String suffix;

}
