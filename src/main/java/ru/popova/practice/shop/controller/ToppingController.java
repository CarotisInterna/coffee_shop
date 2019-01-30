package ru.popova.practice.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.popova.practice.shop.dto.PageDto;
import ru.popova.practice.shop.dto.ToppingDto;
import ru.popova.practice.shop.mapper.ToppingMapper;
import ru.popova.practice.shop.service.ToppingService;

import java.util.List;

@RestController
@RequestMapping("/api/toppings")
public class ToppingController {

    private ToppingService toppingService;

    /**
     * получение списка топпингов
     *
     * @param pageable
     * @return список топпингов
     */
    @GetMapping
    public ResponseEntity<PageDto<ToppingDto>> getToppings(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(toppingService.getToppings(pageable));
    }


    @Autowired
    public ToppingController(ToppingService toppingService) {
        this.toppingService = toppingService;
    }
}
