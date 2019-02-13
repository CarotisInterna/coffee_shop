package ru.popova.practice.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.popova.practice.shop.dto.AppUserDto;
import ru.popova.practice.shop.dto.AppUserLoginDto;
import ru.popova.practice.shop.dto.NewAppUserDto;
import ru.popova.practice.shop.exception.ValidationException;
import ru.popova.practice.shop.service.AppUserService;
import ru.popova.practice.shop.service.security.SecurityService;

@RestController
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;

    private final SecurityService securityService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Validated @RequestBody NewAppUserDto newAppUserDto, BindingResult result) {
        if (result.hasErrors()) {
            throw new ValidationException(result);
        }

        appUserService.saveAppUser(newAppUserDto);

        securityService.authenticateUser(newAppUserDto);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody AppUserLoginDto appUserLoginDto, BindingResult result) {
        if (result.hasErrors()) {
            throw new ValidationException(result);
        }

        String name = appUserLoginDto.getUsername();
        AppUserDto appUserByUsername = appUserService.getAppUserByUsername(name);
        if (appUserByUsername == null) {
            throw new UsernameNotFoundException("такого пользователя нет");
        }

        String password = appUserLoginDto.getPassword();
        if (!bCryptPasswordEncoder.matches(password, appUserByUsername.getPassword())) {
            throw  new BadCredentialsException("неверный пароль");
        }

        securityService.authenticateUser(appUserLoginDto);

        return ResponseEntity.ok().build();
    }
}



