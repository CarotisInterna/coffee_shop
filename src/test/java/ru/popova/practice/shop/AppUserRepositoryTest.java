package ru.popova.practice.shop;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import ru.popova.practice.shop.entity.AppUserEntity;
import ru.popova.practice.shop.repository.AppUserEntityRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AppUserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AppUserEntityRepository appUserEntityRepository;

    @Test
    public void Test() {
        AppUserEntity user = new AppUserEntity(1, "alex", "alex", "123456789", "asasasasasa");
    }

}
