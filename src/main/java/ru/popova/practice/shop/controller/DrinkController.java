package ru.popova.practice.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.popova.practice.shop.dto.DrinkDto;
import ru.popova.practice.shop.service.DrinkService;

import java.util.List;


@RestController
@RequestMapping("/drinks")
public class DrinkController {

    private DrinkService drinkService;

    @GetMapping("/")
    public List<DrinkDto> getDrinks(@PageableDefault Pageable pageable) {
        Page<DrinkDto> drinks = drinkService.getDrinks(pageable);
        return drinks.getContent();
    }

    @Autowired
    public DrinkController(DrinkService drinkService) {
        this.drinkService = drinkService;
    }
}
