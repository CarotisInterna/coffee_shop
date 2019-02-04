package ru.popova.practice.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.popova.practice.shop.entity.AppUserEntity;
import ru.popova.practice.shop.service.AppUserService;
import ru.popova.practice.shop.service.security.AuthenticationService;

@Controller
@RequestMapping("/login")
public class AppUserController {

    private AppUserService appUserService;
    private AuthenticationService authenticationService;
}



