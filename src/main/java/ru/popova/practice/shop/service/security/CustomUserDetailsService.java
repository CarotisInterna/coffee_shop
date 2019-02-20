package ru.popova.practice.shop.service.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.popova.practice.shop.entity.AppUserEntity;
import ru.popova.practice.shop.repository.AppUserEntityRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AppUserEntityRepository appUserEntityRepository;

    /**
     * Поиск пользователя по имени пользователя
     * @param username имя пользователя
     * @return информация о пользователе
     * @throws UsernameNotFoundException если имя пользователя не найдено
     * @see CustomUserDetails
     */
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUserEntity appUserEntity = appUserEntityRepository.findAppUserEntityByUsername(username);
        if (appUserEntity == null) {
            throw new UsernameNotFoundException("UserExists.message");
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + appUserEntity.getRole().getCode().toString()));

        return CustomUserDetails
                .builder()
                .username(appUserEntity.getUsername())
                .password(appUserEntity.getPassword())
//                .password(passwordEncoder.encode(appUserEntity.getPassword()))
                .grantedAuthorities(grantedAuthorities)
                .build();
    }
}
