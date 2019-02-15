package ru.popova.practice.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.popova.practice.shop.dto.AppUserDto;
import ru.popova.practice.shop.dto.AppUserLoginDto;
import ru.popova.practice.shop.dto.NewAppUserDto;
import ru.popova.practice.shop.dto.groups.NotEmptyValidationSequence;
import ru.popova.practice.shop.service.AppUserService;
import ru.popova.practice.shop.service.security.SecurityService;

@RestController
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;

    private final SecurityService securityService;

    @PostMapping("/register")
    public ResponseEntity<AppUserDto> register(@Validated @RequestBody NewAppUserDto newAppUserDto, BindingResult result) {

        AppUserDto saved = appUserService.saveAppUser(newAppUserDto, result);

        securityService.authenticateUser(newAppUserDto);

        return ResponseEntity.ok(saved);
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody @Validated(NotEmptyValidationSequence.class) AppUserLoginDto appUserLoginDto, BindingResult result) {

        appUserService.handleLoginExceptions(appUserLoginDto, result);

        securityService.authenticateUser(appUserLoginDto);

        return ResponseEntity.ok().build();
    }
}



