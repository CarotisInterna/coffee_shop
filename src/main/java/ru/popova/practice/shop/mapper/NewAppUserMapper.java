package ru.popova.practice.shop.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ru.popova.practice.shop.dto.NewAppUserDto;
import ru.popova.practice.shop.entity.AppUserEntity;
import ru.popova.practice.shop.entity.code.RoleCode;
import ru.popova.practice.shop.repository.RoleEntityRepository;

@Component
public class NewAppUserMapper implements AbstractMapper<AppUserEntity, NewAppUserDto> {

    private RoleEntityRepository roleEntityRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public NewAppUserMapper(RoleEntityRepository roleEntityRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.roleEntityRepository = roleEntityRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public NewAppUserDto toDto(AppUserEntity entity) {
        return null;
    }

    @Override
    public AppUserEntity toEntity(NewAppUserDto newAppUserDto) {
        if(newAppUserDto == null) {
            return null;
        }

        AppUserEntity appUser = new AppUserEntity();
        appUser.setUsername(newAppUserDto.getUsername());
        appUser.setPassword(bCryptPasswordEncoder.encode(newAppUserDto.getPassword()));
        appUser.setAddress(newAppUserDto.getAddress());
        appUser.setFullName(newAppUserDto.getFullName());
        appUser.setRole(roleEntityRepository.findRoleEntityByCode(RoleCode.USER));
        appUser.setPhoneNumber(newAppUserDto.getPhoneNumber());

        return appUser;
    }
}
