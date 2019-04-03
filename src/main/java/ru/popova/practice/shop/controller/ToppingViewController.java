package ru.popova.practice.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.popova.practice.shop.dto.ToppingDto;
import ru.popova.practice.shop.service.ToppingService;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ToppingViewController {
    private final ToppingService toppingService;

    @GetMapping("/edit/toppings")
    public String editToppings(Model model) {
        model.addAttribute("editMessage", "Редактирование топпингов");
        model.addAttribute("items", toppingService.getToppings());
        model.addAttribute("link", "/edit/toppings");
        return "edit";
    }

    @GetMapping("/create/toppings")
    public String createTopping(Model model) {
        model.addAttribute("message", "Создание топпинга");
        model.addAttribute("topping", new ToppingDto());
        return "topping";
    }

    @GetMapping("/edit/toppings/{id}")
    public String editTopping(@PathVariable("id") Integer id, Model model) {
        Optional<ToppingDto> topping = toppingService.getToppingById(id);
        model.addAttribute("message", "Редактирование топпинга");
        model.addAttribute("topping", topping.orElse(null));
        return "topping";
    }
}
