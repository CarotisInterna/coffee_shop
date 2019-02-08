package ru.popova.practice.shop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.popova.practice.shop.dto.AppUserDto;
import ru.popova.practice.shop.dto.NewAppUserDto;
import ru.popova.practice.shop.entity.AppUserEntity;
import ru.popova.practice.shop.exception.AlreadyExistsException;
import ru.popova.practice.shop.exception.PasswordMismatchException;
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

    /**
     * сохранение нового пользователя
     *
     * @param newAppUserDto новый пользователь
     * @return пользователь
     * @throws AlreadyExistsException    если пользователь с таким именем или номером телефона уже существует
     * @throws PasswordMismatchException если подтверждение пароля не прошло
     */
    @Transactional
    public AppUserDto saveAppUser(NewAppUserDto newAppUserDto) {
        AppUserEntity nameExisted = appUserEntityRepository.findAppUserEntityByUsername(newAppUserDto.getUsername());
        if (nameExisted != null) {
            throw new AlreadyExistsException("username", "Пользователь с таким именем уже существует");
        }
        AppUserEntity phoneExisted = appUserEntityRepository.findAppUserEntityByPhoneNumber(newAppUserDto.getPhoneNumber());
        if (phoneExisted != null) {
            throw new AlreadyExistsException("phoneNumber", "Пользователь с таким номером телефона уже существует");
        }
        if (!newAppUserDto.getPassword().equals(newAppUserDto.getConfirmPassword())) {
            throw new PasswordMismatchException("password", "Пароль не подтвержден");
        }
        AppUserEntity appUserEntity = newAppUserMapper.toEntity(newAppUserDto);
        AppUserEntity saved = appUserEntityRepository.save(appUserEntity);
        log.info("{}", saved.getId());
        return appUserMapper.toDto(saved);
    }

}
