package ru.popova.practice.shop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.popova.practice.shop.Application;
import ru.popova.practice.shop.dto.NewDrinkDto;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.popova.practice.shop.TestUtils.createBananaCocktail;
import static ru.popova.practice.shop.TestUtils.createNewDrink;

@RunWith(SpringRunner.class)
@ContextConfiguration
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class DrinkControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    /**
     * Тест получения напитков
     *
     * @throws Exception
     */
    @Test
    public void getDrinksTest() throws Exception {
        this.mockMvc
                .perform(get("/api/drinks"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    /**
     * Тест поиска напитка по параметрам
     *
     * @throws Exception
     */
    @Test
    public void searchDrinkTest() throws Exception {

        String name = "америка";

        String americano = "Американо";

        this.mockMvc
                .perform(get("/api/drinks/search").param("name", name))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value(americano));

        /*
          Идентификатор категории "Чай"
         */
        Integer categoryId = 4;

        this.mockMvc
                .perform(get("/api/drinks/search").param("name", name).param("category_id", categoryId.toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isEmpty());

    }

    /**
     * Тест сохранения напитка для неавторизованного пользователя
     *
     * @throws Exception
     */
    @Test
    public void saveDrinkTestUnauthorized() throws Exception {

        NewDrinkDto bananaCocktail = createBananaCocktail();

        this.mockMvc
                .perform(post("/api/drinks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bananaCocktail)))
                .andExpect(status().isForbidden());
    }

    /**
     * Тест сохранения напитка для продавца
     *
     * @throws Exception
     */
    @Test
    @WithMockUser(roles = "VENDOR")
    public void saveDrinkAsVendor() throws Exception {


        NewDrinkDto bananaCocktail = createBananaCocktail();

        this.mockMvc
                .perform(post("/api/drinks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bananaCocktail)))
                .andExpect(status().isOk());
    }

    /**
     * Тест сохранения уже существующего напитка
     *
     * @throws Exception
     */
    @Test
    @WithMockUser(roles = "VENDOR")
    public void saveAlreadyExistsDrink() throws Exception {

        NewDrinkDto americano = createNewDrink("Американо",
                150,
                150,
                "Десертный напиток на основе молока и мороженого.",
                2, 5
        );

        this.mockMvc
                .perform(post("/api/drinks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(americano)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorDtos[0].message").value("Напиток с таким наименованием в таком объеме уже существует"));
    }

    /**
     * Тест валидационных сообщений
     *
     * @throws Exception
     */
    @Test
    @WithMockUser(roles = "VENDOR")
    public void testDrinkValidation() throws Exception {

        /*
        валидация имени
         */
        NewDrinkDto drink = createNewDrink("А",
                500,
                100,
                "Десеhjhjhjh",
                2
        );

        this.mockMvc
                .perform(post("/api/drinks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(drink)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorDtos[0].message").value("Может быть от 3 до 20"));

        /*
        валидация цены
         */
        drink.setName("Новый напиток");
        drink.setPrice(new BigDecimal(5005));
        this.mockMvc
                .perform(post("/api/drinks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(drink)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorDtos[0].message").value("Не может быть больше 1000"));

    }

    /**
     * Тест попытки сохранения несуществующей категории
     *
     * @throws Exception
     */
    @Test
    @WithMockUser(roles = "VENDOR")
    public void testCategoryDoesNotExist() throws Exception {

        NewDrinkDto drink = createNewDrink("Новый напиток",
                500,
                100,
                "Десеhjhjhjh",
                2, 100
        );

        this.mockMvc
                .perform(post("/api/drinks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(drink)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorDtos[0].message").value("100 Категория не найдена"));

    }

    /**
     * Тест редактирования не существующего напитка
     *
     * @throws Exception
     */
    @Test
    @WithMockUser(roles = "VENDOR")
    public void testEditNotExistedDrink() throws Exception {

        NewDrinkDto drink = createBananaCocktail();

        this.mockMvc
                .perform(put("/api/drinks/100")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(drink)))
                .andExpect(status().isNotFound());
    }
}
