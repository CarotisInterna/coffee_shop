package ru.popova.practice.shop.service.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.popova.practice.shop.config.messages.MessageSourceDecorator;
import ru.popova.practice.shop.dto.AppUserLoginDto;
import ru.popova.practice.shop.entity.AppUserEntity;
import ru.popova.practice.shop.exception.AuthenticationFailedException;
import ru.popova.practice.shop.repository.AppUserEntityRepository;

import java.util.stream.Stream;

import static ru.popova.practice.shop.util.MessageConstants.AUTHENTICATION_REQUIRED;

@Service
@RequiredArgsConstructor
public class SecurityService {

    private final AuthenticationManager authenticationManager;
    private final MessageSourceDecorator messageSourceDecorator;

    /**
     * Логин пользователя
     *
     * @param appUserLoginDto данные пользователя для логина
     */
    public void authenticateUser(AppUserLoginDto appUserLoginDto) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(appUserLoginDto.getUsername(), appUserLoginDto.getPassword());
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    /**
     * Аутентификация пользователя
     */
    public void checkAuthenticateUser() {
        if (!SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            throw new AuthenticationFailedException("user", messageSourceDecorator.getMessage(AUTHENTICATION_REQUIRED));
        }
    }

    /**
     * Получение имени авторизованного пользователя
     *
     * @return имя пользователя
     */
    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            throw new AuthenticationFailedException("user",  messageSourceDecorator.getMessage(AUTHENTICATION_REQUIRED));
        } else {
            return authentication.getName();
        }
    }
}
