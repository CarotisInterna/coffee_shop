package ru.popova.practice.shop.mapper;

import org.springframework.stereotype.Component;
import ru.popova.practice.shop.dto.AppUserDto;
import ru.popova.practice.shop.entity.AppUserEntity;

@Component
public class AppUserMapper implements AbstractMapper<AppUserEntity, AppUserDto> {

    @Override
    public AppUserDto toDto(AppUserEntity appUserEntity) {
        if (appUserEntity == null) {
            return null;
        }

        AppUserDto appUser = new AppUserDto();
        appUser.setUsername(appUserEntity.getUsername());
        appUser.setPassword(appUserEntity.getPassword());
        appUser.setAddress(appUserEntity.getAddress());
        appUser.setFullName(appUserEntity.getFullName());
        appUser.setPhoneNumber(appUserEntity.getPhoneNumber());

        return appUser;
    }

    @Override
    public AppUserEntity toEntity(AppUserDto dto) {
        return null;
    }
}
