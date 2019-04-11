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
     * @return статус
     */
    @PutMapping("/place")
    public ResponseEntity<OrderDto> placeOrder() {

        OrderDto placedOrderDto = cartService.placeOrder();
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
