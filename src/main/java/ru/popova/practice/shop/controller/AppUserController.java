package ru.popova.practice.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.popova.practice.shop.dto.NewAppUserDto;
import ru.popova.practice.shop.service.AppUserService;
import ru.popova.practice.shop.service.security.SecurityService;

import javax.validation.Valid;

@Controller
@RequestMapping("/register")
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;
    private final SecurityService securityService;

    @PostMapping
    public String register(@Valid @ModelAttribute("appUser") NewAppUserDto newAppUserDto) {

    }
}



