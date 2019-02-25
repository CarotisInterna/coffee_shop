package ru.popova.practice.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.popova.practice.shop.config.messages.MessageSourceDecorator;
import ru.popova.practice.shop.dto.DrinkOrderDto;
import ru.popova.practice.shop.dto.OrderDto;
import ru.popova.practice.shop.entity.*;
import ru.popova.practice.shop.entity.code.OrderStatusCode;
import ru.popova.practice.shop.exception.InvalidOperationException;
import ru.popova.practice.shop.mapper.AppUserMapper;
import ru.popova.practice.shop.mapper.DrinkOrderMapper;
import ru.popova.practice.shop.mapper.OrderMapper;
import ru.popova.practice.shop.repository.DrinkOrderEntityRepository;
import ru.popova.practice.shop.repository.OrderEntityRepository;
import ru.popova.practice.shop.repository.OrderStatusEntityRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final AppUserService appUserService;
    private final OrderMapper orderMapper;
    private final DrinkOrderMapper drinkOrderMapper;
    private final OrderEntityRepository orderEntityRepository;
    private final OrderStatusEntityRepository orderStatusEntityRepository;
    private final DrinkOrderEntityRepository drinkOrderEntityRepository;
    private final MessageSourceDecorator message;

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

    /**
     * Добавление напитка в корзину
     *
     * @param drinkOrderDto дто напитка в заказе
     * @return дто сохраненного напитка в заказе
     */
    @Transactional
    public OrderDto addProductToCart(DrinkOrderDto drinkOrderDto) {
        OrderEntity cart = getCurrentUserCartEntity().orElse(getEmptyCartEntity());
        cart.addDrink(drinkOrderMapper.toEntity(drinkOrderDto));
        setTotalInOrder(cart);
        OrderEntity saved = orderEntityRepository.save(cart);
        return orderMapper.toDto(saved);
    }

    /**
     * Удаление напитка из корзины
     *
     * @param drinkOrderId идентификатор напитка в заказе
     */
    @Transactional
    public void deleteProductFromCart(Integer drinkOrderId) {
        OrderEntity cart = getCurrentUserCartEntity().orElseThrow(() -> new InvalidOperationException("cart", message.getMessage("CartIsEmpty.message")));
        drinkOrderEntityRepository.findById(drinkOrderId).ifPresent(drinkOrderEntityRepository::delete);
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
    private Optional<OrderEntity> getCurrentUserCartEntity() {
        Integer userId = appUserService.getCurrentUser().getId();
        return orderEntityRepository.findOneByAppUserIdAndOrderStatusCode(userId, OrderStatusCode.IN_CART);
    }

    /**
     * Подсчет итога корзины
     *
     * @param order сущность заказа
     */
    @Transactional
    public void setTotalInOrder(OrderEntity order) {
        List<DrinkOrderEntity> drinks = order.getDrinks();
        BigDecimal total = BigDecimal.ZERO;

        for (DrinkOrderEntity drink : drinks) {

            DrinkEntity drinkEntity = drink.getDrink();
            BigDecimal drinkQuantity = new BigDecimal(drink.getQuantity());
            total = total.add(drinkEntity.getPrice()
                    .multiply(drinkQuantity));
            List<ToppingForDrinkInOrderEntity> toppings = drink.getToppings();

            for (ToppingForDrinkInOrderEntity topping : toppings) {

                ToppingEntity toppingEntity = topping.getTopping();
                BigDecimal toppingQuantity = new BigDecimal(topping.getQuantity());
                total = total.add(toppingEntity.getPrice()
                        .multiply(toppingQuantity));

            }
        }

        order.setTotal(total);
    }

}
