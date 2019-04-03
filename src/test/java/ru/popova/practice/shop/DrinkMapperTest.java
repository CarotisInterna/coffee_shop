package ru.popova.practice.shop;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.popova.practice.shop.dto.DrinkDto;
import ru.popova.practice.shop.entity.CategoryEntity;
import ru.popova.practice.shop.entity.DrinkEntity;
import ru.popova.practice.shop.mapper.DrinkMapper;
import ru.popova.practice.shop.repository.CategoryEntityRepository;

import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static ru.popova.practice.shop.TestUtils.*;

public class DrinkMapperTest {

    @Mock
    private CategoryEntityRepository categoryEntityRepository;

    @InjectMocks
    private DrinkMapper drinkMapper;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Тест маппинга сущности в дто
     */
    @Test
    public void testToDto() {
        DrinkEntity entity = createBananaCocktailEntity();
        DrinkDto expected = createBananaCocktailDto();

        DrinkDto actual = drinkMapper.toDto(entity);

        assertNotNull(actual);
        assertEquals(actual.getName(), expected.getName());
        assertEquals(actual.getPrice(), expected.getPrice());
        assertEquals(actual.getDescription(), expected.getDescription());
        assertEquals(actual.getVolume(), expected.getVolume());
        assertEquals(actual.getCategories(), expected.getCategories());
    }

    /**
     * Тест маппинга дто в сущность
     */
    @Test
    public void testToEntity() {
        DrinkEntity expected = createBananaCocktailEntity();
        DrinkDto dto = createBananaCocktailDto();
        String catecory2 = "test_category_2";
        String catecory5 = "test_category_5";

        when(categoryEntityRepository.findCategoryEntityByName(catecory2)).thenReturn(getCategoryEntity(2));
        when(categoryEntityRepository.findCategoryEntityByName(catecory5)).thenReturn(getCategoryEntity(5));

        DrinkEntity actual = drinkMapper.toEntity(dto);

        assertNotNull(actual);
        assertEquals(actual.getName(), expected.getName());
        assertEquals(actual.getPrice(), expected.getPrice());
        assertEquals(actual.getDescription(), expected.getDescription());
        assertEquals(actual.getVolume(), expected.getVolume());
        assertEquals(actual.getCategories().stream().map(CategoryEntity::getName).collect(Collectors.toList()), expected.getCategories().stream().map(CategoryEntity::getName).collect(Collectors.toList()));
    }

}
