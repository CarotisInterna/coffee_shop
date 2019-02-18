package ru.popova.practice.shop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import ru.popova.practice.shop.config.messages.MessageSourceDecorator;
import ru.popova.practice.shop.dto.AppUserDto;
import ru.popova.practice.shop.dto.AppUserLoginDto;
import ru.popova.practice.shop.dto.ListErrorDto;
import ru.popova.practice.shop.dto.NewAppUserDto;
import ru.popova.practice.shop.entity.AppUserEntity;
import ru.popova.practice.shop.exception.AlreadyExistsException;
import ru.popova.practice.shop.exception.PasswordMismatchException;
import ru.popova.practice.shop.exception.ValidationException;
import ru.popova.practice.shop.mapper.AppUserMapper;
import ru.popova.practice.shop.mapper.NewAppUserMapper;
import ru.popova.practice.shop.repository.AppUserEntityRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppUserService {

    private final AppUserEntityRepository appUserEntityRepository;
    private final PasswordEncoder passwordEncoder;
    private final NewAppUserMapper newAppUserMapper;
    private final AppUserMapper appUserMapper;
    private final MessageSourceDecorator messageSourceDecorator;

    /**
     * поиск пользователя по имени пользователя
     *
     * @param username имя пользователя
     * @return пользователь
     */
    @Transactional
    public AppUserDto getAppUserByUsername(String username) {
        return appUserMapper.toDto(appUserEntityRepository.findAppUserEntityByUsername(username));
    }

    /**
     * поиск пользователя по номеру телефона
     *
     * @param phoneNumber номер телефона
     * @return пользователь
     */

    @Transactional
    public AppUserDto getAppUserByPhoneNumber(String phoneNumber) {
        return appUserMapper.toDto(appUserEntityRepository.findAppUserEntityByPhoneNumber(phoneNumber));
    }

    /**
     * сохранение нового пользователя
     *
     * @param newAppUserDto новый пользователь
     * @return пользователь
     * @throws AlreadyExistsException если пользователь с таким именем или номером телефона уже существует
     */
    @Transactional
    public AppUserDto saveAppUser(NewAppUserDto newAppUserDto, BindingResult bindingResult) {

        ListErrorDto listErrorDto = new ListErrorDto();

        if(!newAppUserDto.getPassword().equals(newAppUserDto.getConfirmPassword())) {
            listErrorDto.addErrorDto("confirmPassword", messageSourceDecorator.getMessage("ConfirmPassword.message"));
        }

        if (bindingResult.hasErrors() || !listErrorDto.getErrorDtos().isEmpty()) {
            throw new ValidationException(bindingResult, listErrorDto);
        }

        AppUserEntity appUserEntity = newAppUserMapper.toEntity(newAppUserDto);
        AppUserEntity saved = appUserEntityRepository.save(appUserEntity);
        log.info("{}", saved.getId());
        return appUserMapper.toDto(saved);
    }

    /**
     * "Выбрасывание" ошибок при логине
     *
     * @param appUserLoginDto данные пользователя для логина
     * @param bindingResult
     */
    @Transactional
    public void  checkLogin(AppUserLoginDto appUserLoginDto, BindingResult bindingResult) {

        ListErrorDto listErrorDto = new ListErrorDto();

        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult, listErrorDto);
        }

        String name = appUserLoginDto.getUsername();
        AppUserDto appUserByUsername = getAppUserByUsername(name);
        if (appUserByUsername == null) {
            throw new UsernameNotFoundException(messageSourceDecorator.getMessage("UsernameNotFoundException.message"));
        }

        String password = appUserLoginDto.getPassword();
        if (!passwordEncoder.matches(password, appUserByUsername.getPassword())) {
            throw new PasswordMismatchException("password", messageSourceDecorator.getMessage("Password.message"));
        }
    }

}
