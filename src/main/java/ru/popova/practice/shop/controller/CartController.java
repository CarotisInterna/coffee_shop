package ru.popova.practice.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.popova.practice.shop.dto.DrinkOrderDto;
import ru.popova.practice.shop.dto.OrderDto;
import ru.popova.practice.shop.dto.groups.NotEmptyValidationSequence;
import ru.popova.practice.shop.exception.ValidationException;
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
    public ResponseEntity<OrderDto> addToCart(@RequestBody @Validated(NotEmptyValidationSequence.class) DrinkOrderDto drinkOrderDto,
                                              BindingResult result) {
        if (result.hasErrors()) {
            throw new ValidationException(result);
        }

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
    @DeleteMapping("/{id}")
    public ResponseEntity deleteDrinkFromCart(@PathVariable Integer id) {
        cartService.deleteProductFromCart(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Редактирование напитка в корзине
     *
     * @param drinkOrderDto изменения в напитке в корзине
     * @param drinkOrderId  идентификатор напитка в корзине
     * @param result
     * @return статус
     */
    @PutMapping("/{drinkOrderId}")
    public ResponseEntity<OrderDto> editDrinkInCart(@RequestBody @Validated(NotEmptyValidationSequence.class) DrinkOrderDto drinkOrderDto,
                                                    @PathVariable Integer drinkOrderId,
                                                    BindingResult result) {
        if (result.hasErrors()) {
            throw new ValidationException(result);
        }

        OrderDto orderDto = cartService.editCart(drinkOrderDto, drinkOrderId);
        return ResponseEntity.ok(orderDto);
    }
}
