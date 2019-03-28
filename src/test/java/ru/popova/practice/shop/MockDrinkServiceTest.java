package ru.popova.practice.shop;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.test.context.support.WithMockUser;
import ru.popova.practice.shop.dto.NewDrinkDto;
import ru.popova.practice.shop.entity.DrinkEntity;
import ru.popova.practice.shop.entity.DrinkImageEntity;
import ru.popova.practice.shop.mapper.DrinkMapper;
import ru.popova.practice.shop.mapper.NewDrinkMapper;
import ru.popova.practice.shop.repository.DrinkEntityRepository;
import ru.popova.practice.shop.service.DrinkService;
import ru.popova.practice.shop.service.ImageService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MockDrinkServiceTest extends AbstractTest {

    @Mock
    NewDrinkMapper newDrinkMapper;

    @Mock
    DrinkEntityRepository drinkEntityRepository;

    @Mock
    ImageService imageService;

    @Mock
    DrinkMapper drinkMapper;

    @InjectMocks
    DrinkService drinkService;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
    }

    @Test
    @WithMockUser(roles = "VENDOR")
    public void testSaveDrink() {


        when(drinkEntityRepository.save(any(DrinkEntity.class))).thenReturn(new DrinkEntity());

        NewDrinkDto bananaCocktail = createBananaCocktail();
        DrinkEntity drinkEntity = newDrinkMapper.toEntity(bananaCocktail);
        DrinkEntity saved = drinkEntityRepository.save(drinkEntity);
        List<DrinkImageEntity> imageEntities = imageService.saveImages(bananaCocktail.getImages(), saved);

        assertThat(drinkService.saveDrink(bananaCocktail), is(saved));

    }
}
