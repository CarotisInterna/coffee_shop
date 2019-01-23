package ru.popova.practice.shop;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.popova.practice.shop.entity.AppUserEntity;
import ru.popova.practice.shop.entity.RoleEntity;
import ru.popova.practice.shop.entity.id.RoleId;
import ru.popova.practice.shop.repository.AppUserEntityRepository;
import ru.popova.practice.shop.repository.RoleEntityRepository;

import java.util.Collections;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@RunWith(SpringRunner.class)
@SpringBootTest
public class AppUserRepositoryTest {


    @Autowired
    private AppUserEntityRepository appUserEntityRepository;

    @Autowired
    private RoleEntityRepository roleEntityRepository;

    @Test
    public void test() {
        RoleEntity role = new RoleEntity(RoleId.CUSTOMER, RoleId.CUSTOMER.name());
        AppUserEntity user = new AppUserEntity(
                1,
                "alex",
                "alex",
                "123",
                "123456789",
                "asasasasasa",
                role,
                Collections.emptyList());
        roleEntityRepository.save(role);
        appUserEntityRepository.save(user);
    }

}