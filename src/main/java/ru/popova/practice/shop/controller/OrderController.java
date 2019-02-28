package ru.popova.practice.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.popova.practice.shop.dto.OrderDto;
import ru.popova.practice.shop.dto.groups.NotEmptyValidationSequence;
import ru.popova.practice.shop.exception.ValidationException;
import ru.popova.practice.shop.service.CartService;

@RestController
@RequestMapping("/api/order/{orderId}")
@RequiredArgsConstructor
public class OrderController {

    private final CartService cartService;

    /**
     * Размещение заказа
     *
     * @param orderDto дто заказа
     * @param result
     * @return статус
     */
    @PutMapping("/place")
    public ResponseEntity<OrderDto> placeOrder(@RequestBody @Validated(NotEmptyValidationSequence.class) OrderDto orderDto,
                                               BindingResult result) {

        if (result.hasErrors()) {
            throw new ValidationException(result);
        }

        OrderDto placedOrderDto = cartService.placeOrder(orderDto);
        return ResponseEntity.ok(placedOrderDto);
    }

    /**
     * Отмена заказа
     *
     * @param orderId дто заказа
     * @return статус
     */
    @PutMapping("/reject")
    public ResponseEntity<OrderDto> rejectOrder(@PathVariable Integer orderId) {

        OrderDto placedOrderDto = cartService.rejectOrder(orderId);
        return ResponseEntity.ok(placedOrderDto);

    }

    /**
     * Доставка заказа
     *
     * @param orderId дто заказа
     * @return статус
     */
    @PutMapping("/deliver")
    public ResponseEntity<OrderDto> deliverOrder(@PathVariable Integer orderId) {

        OrderDto placedOrderDto = cartService.deliverOrder(orderId);
        return ResponseEntity.ok(placedOrderDto);

    }
}
