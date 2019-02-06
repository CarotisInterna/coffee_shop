package ru.popova.practice.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.popova.practice.shop.entity.AppUserEntity;
import ru.popova.practice.shop.repository.AppUserEntityRepository;

@Service
@RequiredArgsConstructor
public class AppUserService {

    private final AppUserEntityRepository appUserEntityRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * поиск пользователя по имени пользователя
     *
     * @param username имя пользователя
     * @return сущность пользователя
     */
    @Transactional
    public AppUserEntity getAppUserByUsername(String username) {
        return appUserEntityRepository.findAppUserEntityByUsername(username);
    }

}
