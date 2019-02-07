package ru.popova.practice.shop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.popova.practice.shop.dto.AppUserDto;
import ru.popova.practice.shop.dto.NewAppUserDto;
import ru.popova.practice.shop.entity.AppUserEntity;
import ru.popova.practice.shop.mapper.AppUserMapper;
import ru.popova.practice.shop.mapper.NewAppUserMapper;
import ru.popova.practice.shop.repository.AppUserEntityRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppUserService {

    private final AppUserEntityRepository appUserEntityRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final NewAppUserMapper newAppUserMapper;
    private final AppUserMapper appUserMapper;

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

    @Transactional
    public AppUserDto saveAppUser(NewAppUserDto newAppUserDto) {
        AppUserEntity appUserEntity = newAppUserMapper.toEntity(newAppUserDto);
        AppUserEntity saved = appUserEntityRepository.save(appUserEntity);
        log.info("{}", saved.getId());
        return appUserMapper.toDto(saved);
    }

}
