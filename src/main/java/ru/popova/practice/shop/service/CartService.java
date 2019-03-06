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
import ru.popova.practice.shop.exception.NotFoundException;
import ru.popova.practice.shop.mapper.DrinkOrderMapper;
import ru.popova.practice.shop.mapper.OrderMapper;
import ru.popova.practice.shop.repository.DrinkOrderEntityRepository;
import ru.popova.practice.shop.repository.OrderEntityRepository;
import ru.popova.practice.shop.repository.OrderStatusEntityRepository;
import ru.popova.practice.shop.repository.ToppingForDrinkInOrderEntityRepository;
import ru.popova.practice.shop.util.OrderAction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static ru.popova.practice.shop.util.MessageConstants.*;
import static ru.popova.practice.shop.util.ObjectConstants.ORDER_OBJECT;

@Service
@RequiredArgsConstructor
public class CartService {

    private final AppUserService appUserService;
    private final OrderMapper orderMapper;
    private final DrinkOrderMapper drinkOrderMapper;
    private final OrderEntityRepository orderEntityRepository;
    private final OrderStatusEntityRepository orderStatusEntityRepository;
    private final DrinkOrderEntityRepository drinkOrderEntityRepository;
    private final ToppingForDrinkInOrderEntityRepository toppingForDrinkInOrderEntityRepository;
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
        cart.setTotal(calculateTotal(cart));
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
        OrderEntity cart = getCurrentUserCartEntity().orElseThrow(() -> new InvalidOperationException("cart", message.getMessage(CART_IS_EMPTY)));
        drinkOrderEntityRepository.findById(drinkOrderId).ifPresent(drinkOrderEntityRepository::delete);
        cart.setTotal(calculateTotal(cart));
        //TODO: save cart
    }

    /**
     * Редакетирование напитка в корзине
     *
     * @param drinkOrderDto дто напитка в корзине
     * @param drinkOrderId  идентификатор напитка в корзине
     * @return отредактированный напиток в корзине
     */
    @Transactional
    public OrderDto editCart(DrinkOrderDto drinkOrderDto, Integer drinkOrderId) {

        Optional<DrinkOrderEntity> found = drinkOrderEntityRepository.findById(drinkOrderId);

        if (!found.isPresent()) {
            throw new NotFoundException(message.getMessage(DRINK_ORDER_NOT_FOUND));
        }

        DrinkOrderEntity newInfo = drinkOrderMapper.toEntity(drinkOrderDto);
        DrinkOrderEntity old = found.get();

        old.setQuantity(newInfo.getQuantity());
        for (ToppingForDrinkInOrderEntity topping : newInfo.getToppings()) {
            ToppingForDrinkInOrderEntity savedTopping = toppingForDrinkInOrderEntityRepository.getOne(new ToppingForDrinkInOrderId(drinkOrderId, topping.getId().getToppingId()));
            savedTopping.setQuantity(topping.getQuantity());
            toppingForDrinkInOrderEntityRepository.save(savedTopping);
        }

        drinkOrderEntityRepository.save(old);

        OrderEntity cart = getCurrentUserCartEntity().get();
        cart.setTotal(calculateTotal(cart));
        OrderEntity edited = orderEntityRepository.save(cart);
        return orderMapper.toDto(edited);
    }

    /**
     * Размещение заказа
     *
     * @param orderDto дто заказа
     * @return дто размещенного заказа
     */
    public OrderDto placeOrder(OrderDto orderDto) {

        Integer orderId = orderDto.getId();
        String address = orderDto.getAddress();
        return changeOrderStatus(orderId, OrderAction.PLACE, address);

    }

    /**
     * Отмена заказа
     *
     * @param orderId идентификатор заказа
     * @return дто отмененного заказа
     */
    public OrderDto rejectOrder(Integer orderId) {
        return changeOrderStatus(orderId, OrderAction.REJECT);
    }

    /**
     * Доставка заказа
     *
     * @param orderId идентификатор заказа
     * @return дто доставленного заказа
     */
    public OrderDto deliverOrder(Integer orderId) {
        return changeOrderStatus(orderId, OrderAction.DELIVER);
    }

    /**
     * Изменение статуса заказа
     *
     * @param id      идентификатор заказа
     * @param action  действие, которое необходимо сделать с заказом
     * @param address адрес доставки
     * @return дто заказа с измененным статусом
     */
    @Transactional
    public OrderDto changeOrderStatus(Integer id, OrderAction action, String address) {

        Optional<OrderEntity> found = orderEntityRepository.findById(id);

        if (!found.isPresent()) {
            throw new NotFoundException(message.getMessage(ORDER_NOT_FOUND));
        }

        OrderEntity order = found.get();

        OrderStatusCode currentCode = order.getOrderStatus().getCode();

        OrderStatusCode newCode = getNewOrderStatus(action, currentCode);

        order.setOrderStatus(orderStatusEntityRepository.findOrderStatusEntityByCode(newCode));
        if (action == OrderAction.PLACE) {
            order.setDate(LocalDateTime.now());
            order.setAddress(address);
            order.setTotal(calculateTotal(order));
        }
        OrderEntity changed = orderEntityRepository.save(order);
        return orderMapper.toDto(changed);
    }

    /**
     * Изменение статуса заказа
     *
     * @param id     идентификатор заказа
     * @param action действие, которое необходимо сделать с заказом
     * @return дто заказа с измененным статусом
     */
    @Transactional
    public OrderDto changeOrderStatus(Integer id, OrderAction action) {
        return changeOrderStatus(id, action, null);
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
    public BigDecimal calculateTotal(OrderEntity order) {
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

        return total;
    }

    /**
     * Изменение статуса заказа
     *
     * @param action          действие, в результате которого статус заказа планируется быть измененным
     * @param orderStatusCode код текущего статуса заказа
     * @return код нового статуса заказа
     */
    private OrderStatusCode getNewOrderStatus(OrderAction action, OrderStatusCode orderStatusCode) {
        OrderStatusCode newOrderStatusCode;
        switch (orderStatusCode) {
            case IN_CART:
                if (action == OrderAction.PLACE) {
                    newOrderStatusCode = OrderStatusCode.PLACED;
                } else if (action == OrderAction.DELIVER) {
                    throw new InvalidOperationException(ORDER_OBJECT, message.getMessage(INVALID_DELIVER_ACTION));
                } else if (action == OrderAction.REJECT) {
                    throw new InvalidOperationException(ORDER_OBJECT, message.getMessage(INVALID_REJECT_ACTION));
                } else {
                    throw new InvalidOperationException(ORDER_OBJECT, message.getMessage(UNKNOWN_ACTION));
                }
                break;
            case PLACED:
                if (action == OrderAction.REJECT) {
                    newOrderStatusCode = OrderStatusCode.REJECTED;
                } else if (action == OrderAction.DELIVER) {
                    newOrderStatusCode = OrderStatusCode.DELIVERED;
                } else if (action == OrderAction.PLACE) {
                    throw new InvalidOperationException(ORDER_OBJECT, message.getMessage(INVALID_PLACE_ACTION));
                } else {
                    throw new InvalidOperationException(ORDER_OBJECT, message.getMessage(UNKNOWN_ACTION));
                }
                break;
            case REJECTED:
                throw new InvalidOperationException(ORDER_OBJECT, message.getMessage(REJECT_ORDER));
            case DELIVERED:
                throw new InvalidOperationException(ORDER_OBJECT, message.getMessage(DELIVER_ORDER));
            default:
                throw new InvalidOperationException(ORDER_OBJECT, message.getMessage(UNKNOWN_ACTION));
        }
        return newOrderStatusCode;
    }

}
