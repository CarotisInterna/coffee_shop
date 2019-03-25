package ru.popova.practice.shop;

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
import ru.popova.practice.shop.dto.NewDrinkDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
     * Создание корректного дто напитка для добавления
     *
     * @return
     */
    public NewDrinkDto createBananaCocktail() {

        List<Integer> categories = new ArrayList<>();
        categories.add(2);
        categories.add(5);

        List<String> images = new ArrayList<>();
        images.add("banana_cocktail");

        return new NewDrinkDto(
                "Банановый коктейль",
                new BigDecimal(150),
                300,
                images,
                "Десертный напиток на основе молока и мороженого.",
                categories
        );
    }
}
