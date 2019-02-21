package ru.popova.practice.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.popova.practice.shop.dto.DrinkOrderDto;
import ru.popova.practice.shop.dto.OrderDto;
import ru.popova.practice.shop.entity.AppUserEntity;
import ru.popova.practice.shop.entity.OrderEntity;
import ru.popova.practice.shop.entity.OrderStatusEntity;
import ru.popova.practice.shop.entity.code.OrderStatusCode;
import ru.popova.practice.shop.mapper.AppUserMapper;
import ru.popova.practice.shop.mapper.OrderMapper;
import ru.popova.practice.shop.repository.OrderEntityRepository;
import ru.popova.practice.shop.repository.OrderStatusEntityRepository;
import ru.popova.practice.shop.service.security.SecurityService;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CartService {

    private final SecurityService securityService;
    private final AppUserService appUserService;
    private final AppUserMapper appUserMapper;
    private final OrderMapper orderMapper;
    private final OrderEntityRepository orderEntityRepository;
    private final OrderStatusEntityRepository orderStatusEntityRepository;

    /**
     * Получение корзины текущего пользователя
     *
     * @return корзина текущего пользователя
     */
    @Transactional(readOnly = true)
    public OrderDto getCurrentUserCart() {
        OrderEntity cart = getCurrentUserCartEntity();
        return orderMapper.toDto(cart);
    }

    @Transactional
    public OrderDto addProductToCart(DrinkOrderDto drinkOrderDto) {
        OrderEntity cart = getCurrentUserCartEntity();
        if (cart == null) {
            cart = getEmptyCartEntity();
        }
        drinkOrderDto.setOrder();

    }

    /**
     * Полуение сущности пустой корзины
     *
     * @return сущность пустой зины-корзины
     */
    @Transactional
    public OrderEntity getEmptyCartEntity() {
        OrderEntity orderEntity = new OrderEntity();
        OrderStatusEntity inCart = orderStatusEntityRepository.findOrderStatusEntityByOrderStatusCode(OrderStatusCode.IN_CART);
        AppUserEntity currentAppUser = appUserMapper.toEntity(appUserService.getCurrentUser());
        orderEntity.setOrderStatus(inCart);
        orderEntity.setAppUser(currentAppUser);
        orderEntity.setTotal(BigDecimal.ZERO);
        orderEntity.setAddress("");
        orderEntity.setDate(LocalDateTime.now());
        orderEntity.setDrinks(null);
        return orderEntity;
    }

    /**
     * Получение сущности корзины текущего пользователя
     *
     * @return сущность сыночки-корзиночки текущего пользователя
     */
    public OrderEntity getCurrentUserCartEntity() {
        AppUserEntity currentAppUser = appUserMapper.toEntity(appUserService.getCurrentUser());
        OrderStatusEntity inCart = orderStatusEntityRepository.findOrderStatusEntityByOrderStatusCode(OrderStatusCode.IN_CART);
        return orderEntityRepository.findOrderEntityByAppUserAndOrderStatus(currentAppUser, inCart);
    }

}
