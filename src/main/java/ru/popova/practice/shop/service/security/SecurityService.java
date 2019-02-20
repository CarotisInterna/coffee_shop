package ru.popova.practice.shop.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.popova.practice.shop.dto.AppUserLoginDto;
import ru.popova.practice.shop.exception.AuthenticationFailedException;

import java.util.stream.Stream;

@Service
public class SecurityService {

    private AuthenticationManager authenticationManager;

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
            throw new AuthenticationFailedException("user", "AuthenticationRequired.message");
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
            throw new AuthenticationFailedException("user", "AuthenticationRequired.message");
        } else {
            return authentication.getName();
        }
    }

    @Autowired
    public void setAuthManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

}
