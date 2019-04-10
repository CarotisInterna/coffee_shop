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

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    /**
     * Добавление напитка в корзину
     *
     * @param drinkId    напиток в заказе
     * @param toppingIds топпинги
     * @return статус
     */
    @PostMapping
    public ResponseEntity<OrderDto> addToCart(@RequestParam(value = "drinkId") Integer drinkId,
                                              @RequestParam(value = "toppings", required = false)
                                                      List<Integer> toppingIds) {

        OrderDto saved = cartService.addToCart(drinkId, toppingIds);
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
     * @param drinkOrderId идентификатор напитка в заказе
     * @return статус
     */
    @DeleteMapping("/{drinkOrderId}")
    public ResponseEntity deleteDrinkFromCart(@PathVariable Integer drinkOrderId) {
        cartService.deleteProductFromCart(drinkOrderId);
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
