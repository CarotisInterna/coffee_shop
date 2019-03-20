package ru.popova.practice.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.popova.practice.shop.dto.DrinkDto;
import ru.popova.practice.shop.service.CategoryService;
import ru.popova.practice.shop.service.DrinkService;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class DrinkViewController {
    private final DrinkService drinkService;
    private final CategoryService categoryService;

    @GetMapping("/edit/drinks")
    public String editDrinks(Model model) {
        model.addAttribute("editMessage", "Редактирование напитков");
        List<DrinkDto> content = drinkService.getDrinks(Pageable.unpaged()).getContent();
        model.addAttribute("items", content);
        model.addAttribute("link", "/edit/drinks");
        return "edit";
    }


    @GetMapping("/create/drinks")
    public String createDrink(Model model) {
        model.addAttribute("message", "Создание напитка");
        model.addAttribute("categories", categoryService.getCategories());
        model.addAttribute("drink", new DrinkDto());
        return "drink";
    }

    @GetMapping(("/edit/drinks/{id}"))
    public String editDrink(@PathVariable("id") Integer id, Model model) {
        Optional<DrinkDto> drinkById = drinkService.getDrinkById(id);
        model.addAttribute("message", "Редактирование напитка");
        model.addAttribute("drink", drinkById.orElse(null));
        model.addAttribute("categories", categoryService.getCategories());
        return "drink";
    }
}
