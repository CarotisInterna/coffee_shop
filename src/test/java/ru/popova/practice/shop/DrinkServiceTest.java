package ru.popova.practice.shop;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;
import ru.popova.practice.shop.dto.DrinkDto;
import ru.popova.practice.shop.dto.NewDrinkDto;
import ru.popova.practice.shop.entity.DrinkEntity;
import ru.popova.practice.shop.mapper.DrinkMapper;
import ru.popova.practice.shop.mapper.NewDrinkMapper;
import ru.popova.practice.shop.repository.CategoryEntityRepository;
import ru.popova.practice.shop.repository.DrinkEntityRepository;
import ru.popova.practice.shop.service.DrinkService;
import ru.popova.practice.shop.service.ImageService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static ru.popova.practice.shop.TestUtils.*;

public class DrinkServiceTest {
    private static int counterId = 0;

    @Mock
    private NewDrinkMapper newDrinkMapper;

    @Mock
    private DrinkEntityRepository drinkEntityRepository;

    @Mock
    private CategoryEntityRepository categoryRepository;

    @Mock
    private DrinkMapper drinkMapper;

    @Mock
    private ImageService imageService;

    @InjectMocks
    private DrinkService drinkService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Тест сохранения напитка
     */
    @Test
    @WithMockUser(roles = "VENDOR")
    public void testSaveDrink() {
        NewDrinkDto expected = createBananaCocktail();
        DrinkEntity entity = createBananaCocktailEntity();
        DrinkDto dto = createBananaCocktailDto();

        when(categoryRepository.findById(any())).thenAnswer(invocation -> {
            Integer category = (Integer) invocation.getArguments()[0];
            return Optional.of(getCategoryEntity(category));
        });

        when(drinkEntityRepository.save(any())).thenAnswer(invocation -> {
            DrinkEntity drink = (DrinkEntity) invocation.getArguments()[0];
            if (drink.getId() == null) {
                drink.setId(++counterId);
            }
            return drink;
        });

        when(imageService.saveImages(any(), any())).thenReturn(Collections.emptyList());


        when(newDrinkMapper.toEntity(expected)).thenReturn(entity);

        when(drinkMapper.toDto(any())).thenReturn(dto);


        DrinkDto actual = drinkService.saveDrink(expected);

        assertNotNull(actual);
        assertEquals(actual.getName(), expected.getName());
        assertEquals(actual.getPrice(), expected.getPrice());
        assertEquals(actual.getDescription(), expected.getDescription());
        assertEquals(actual.getVolume(), expected.getVolume());
    }

    /**
     * Тест удаления напитка
     */
    @Test
    @WithMockUser(roles = "VENDOR")
    public void testDeleteDrink() {

        when(drinkEntityRepository.findById(any())).thenAnswer(invocation -> {
            Integer drink = (Integer) invocation.getArguments()[0];
            return Optional.of(getDrinkEntity(drink));
        });

        doNothing().when(drinkEntityRepository).delete(any());

        drinkService.deleteDrink(anyInt());

        verify(drinkEntityRepository, times(1)).delete(any());
    }
}
