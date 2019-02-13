package ru.popova.practice.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.popova.practice.shop.dto.AppUserDto;
import ru.popova.practice.shop.dto.NewAppUserDto;
import ru.popova.practice.shop.exception.ValidationException;
import ru.popova.practice.shop.service.AppUserService;
import ru.popova.practice.shop.service.security.SecurityService;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;
    private final SecurityService securityService;

    @PostMapping
    public ResponseEntity<AppUserDto> register(@Validated @RequestBody NewAppUserDto newAppUserDto, BindingResult result) {
        if (result.hasErrors()) {
            throw new ValidationException(result);
        }
        AppUserDto body = appUserService.saveAppUser(newAppUserDto);
        securityService.authenticateUser(body);
        return ResponseEntity.ok(body);
    }
}



