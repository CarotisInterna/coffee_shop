package ru.popova.practice.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.popova.practice.shop.dto.DrinkOrderDto;
import ru.popova.practice.shop.dto.OrderDto;
import ru.popova.practice.shop.service.CartService;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    /**
     * Добавление напитка в корзину
     * @param drinkOrderDto напиток в заказе
     * @return сохраненный заказ
     */
    @PostMapping
    public ResponseEntity<OrderDto> addToCart(@RequestBody DrinkOrderDto drinkOrderDto) {
        OrderDto saved = cartService.addProductToCart(drinkOrderDto);
        return ResponseEntity.ok(saved);
    }
}
