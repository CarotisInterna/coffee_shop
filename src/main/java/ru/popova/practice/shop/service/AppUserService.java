package ru.popova.practice.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.popova.practice.shop.entity.AppUserEntity;
import ru.popova.practice.shop.repository.AppUserEntityRepository;

import javax.annotation.PostConstruct;

@Service
public class AppUserService {
    private AppUserEntityRepository appUserEntityRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AppUserService(AppUserEntityRepository appUserEntityRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.appUserEntityRepository = appUserEntityRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional
    public AppUserEntity getAppUserByUsername(String username) {
        return appUserEntityRepository.findAppUserEntityByUsername(username);
    }

}
