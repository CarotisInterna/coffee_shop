package ru.popova.practice.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
     *
     * @param drinkOrderDto напиток в заказе
     * @return статус
     */
    @PostMapping
    public ResponseEntity<OrderDto> addToCart(@RequestBody DrinkOrderDto drinkOrderDto) {
        OrderDto saved = cartService.addProductToCart(drinkOrderDto);
        return ResponseEntity.ok(saved);
    }

    /**
     * Получение корзины текущего пользователя
     *
     * @return статус
     */
    @GetMapping
    public ResponseEntity<OrderDto> getCurrentUserCart() {
        return ResponseEntity.ok(cartService.getCurrentUserCart());
    }

    /**
     * Удаление напитка из корзины
     *
     * @param id идентификатор напитка в заказе
     * @return статус
     */
    @DeleteMapping
    public ResponseEntity deleteDrinkFromCart(@RequestBody Integer id) {
        cartService.deleteProductFromCart(id);
        return ResponseEntity.ok().build();
    }
}
