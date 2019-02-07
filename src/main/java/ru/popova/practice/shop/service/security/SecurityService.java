package ru.popova.practice.shop.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.popova.practice.shop.dto.AppUserLoginDto;

@Service
public class SecurityService {

    private AuthenticationManager authenticationManager;

    public void authenticateUser(AppUserLoginDto appUserLoginDto) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(appUserLoginDto.getUsername(), appUserLoginDto.getPassword());
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Autowired
    public void setAuthManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

}
