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
import ru.popova.practice.shop.mapper.DrinkOrderMapper;
import ru.popova.practice.shop.mapper.OrderMapper;
import ru.popova.practice.shop.repository.OrderEntityRepository;
import ru.popova.practice.shop.repository.OrderStatusEntityRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final AppUserService appUserService;
    private final AppUserMapper appUserMapper;
    private final OrderMapper orderMapper;
    private final OrderEntityRepository orderEntityRepository;
    private final OrderStatusEntityRepository orderStatusEntityRepository;
    private final DrinkOrderMapper drinkOrderMapper;

    /**
     * Получение корзины текущего пользователя
     *
     * @return корзина текущего пользователя
     */
    @Transactional(readOnly = true)
    public OrderDto getCurrentUserCart() {
        OrderEntity cart = getCurrentUserCartEntity().orElse(getEmptyCartEntity());
        return orderMapper.toDto(cart);
    }

    @Transactional
    public OrderDto addProductToCart(DrinkOrderDto drinkOrderDto) {
        OrderEntity cart = getCurrentUserCartEntity().orElse(getEmptyCartEntity());
        cart.addDrink(drinkOrderMapper.toEntity(drinkOrderDto));
        OrderEntity saved = orderEntityRepository.save(cart);
        return orderMapper.toDto(saved);
    }

    /**
     * Полуение сущности пустой корзины
     *
     * @return сущность пустой зины-корзины
     */
    @Transactional
    public OrderEntity getEmptyCartEntity() {
        OrderEntity orderEntity = new OrderEntity();
        OrderStatusEntity inCart = orderStatusEntityRepository.findOrderStatusEntityByCode(OrderStatusCode.IN_CART);
        AppUserEntity currentAppUser = appUserService.getCurrentUserEntity();
        orderEntity.setOrderStatus(inCart);
        orderEntity.setAppUser(currentAppUser);
        orderEntity.setTotal(BigDecimal.ZERO);
        orderEntity.setDate(LocalDateTime.now());
        return orderEntity;
    }

    /**
     * Получение сущности корзины текущего пользователя
     *
     * @return сущность сыночки-корзиночки текущего пользователя
     */
    public Optional<OrderEntity> getCurrentUserCartEntity() {
        Integer userId = appUserService.getCurrentUser().getId();
        return orderEntityRepository.findOneByAppUserIdAndOrderStatusCode(userId, OrderStatusCode.IN_CART);
    }

}
