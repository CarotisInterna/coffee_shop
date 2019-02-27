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
import ru.popova.practice.shop.util.ActionStatus;

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
        setTotalInOrder(cart);
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
            throw new NotFoundException(message.getMessage("DrinkOrderNotFound.message"));
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
        setTotalInOrder(cart);
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
        return changeOrderStatus(orderDto, ActionStatus.PLACE);
    }

    /**
     * Отмена заказа
     *
     * @param orderDto дто заказа
     * @return дто отмененного заказа
     */
    public OrderDto rejectOrder(OrderDto orderDto) {
        return changeOrderStatus(orderDto, ActionStatus.REJECT);
    }

    /**
     * Доставка заказа
     *
     * @param orderDto дто заказа
     * @return дто доставленного заказа
     */
    public OrderDto deliverOrder(OrderDto orderDto) {
        return changeOrderStatus(orderDto, ActionStatus.DELIVER);
    }

    /**
     * Изменение статуса заказа
     *
     * @param orderDto дто заказа
     * @return дто заказа с измененным статусом
     */
    @Transactional
    public OrderDto changeOrderStatus(OrderDto orderDto, ActionStatus action) {

        OrderStatusCode currentCode = OrderStatusCode.valueOf(orderDto.getOrderStatus());
        OrderStatusCode newCode = getNewOrderStatus(action, currentCode);

        Optional<DrinkOrderEntity> found = drinkOrderEntityRepository.findById(orderDto.getId());

        if (!found.isPresent()) {
            throw new NotFoundException(message.getMessage("DrinkOrderNotFound.message"));
        }

        orderDto.setOrderStatus(newCode.name());
        OrderEntity order = orderMapper.toEntity(orderDto);
        setTotalInOrder(order);
        OrderEntity changed = orderEntityRepository.save(order);
        return orderMapper.toDto(changed);

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

    /**
     * Получение напитка в корзине по идентификатору
     *
     * @param id идентификатор напитка в корзине
     * @return дто напитка в корзине
     */
    @Transactional
    public Optional<DrinkOrderDto> getDrinkOrderById(Integer id) {
        return drinkOrderEntityRepository.findById(id)
                .map(drinkOrderMapper::toDto);
    }

    /**
     * Изменение статуса заказа
     *
     * @param action          действие, в результате которого статус заказа планируется быть измененным
     * @param orderStatusCode код текущего статуса заказа
     * @return код нового статуса заказа
     */
    private OrderStatusCode getNewOrderStatus(ActionStatus action, OrderStatusCode orderStatusCode) {
        OrderStatusCode newOrderStatusCode;
        switch (orderStatusCode) {
            case IN_CART:
                if (action == ActionStatus.PLACE) {
                    newOrderStatusCode = OrderStatusCode.PLACED;
                } else if (action == ActionStatus.DELIVER) {
                    throw new InvalidOperationException("order", message.getMessage("InvalidDeliverAction.message"));
                } else if (action == ActionStatus.REJECT) {
                    throw new InvalidOperationException("order", message.getMessage("InvalidRejectAction.message"));
                } else {
                    throw new InvalidOperationException("order", message.getMessage("UnknownAction.message"));
                }
                break;
            case PLACED:
                if (action == ActionStatus.REJECT) {
                    newOrderStatusCode = OrderStatusCode.REJECTED;
                } else if (action == ActionStatus.DELIVER) {
                    newOrderStatusCode = OrderStatusCode.DELIVERED;
                } else if (action == ActionStatus.PLACE) {
                    throw new InvalidOperationException("order", message.getMessage("InvalidPlaceAction.message"));
                } else {
                    throw new InvalidOperationException("order", message.getMessage("UnknownAction.message"));
                }
                break;
            case REJECTED:
                throw new InvalidOperationException("order", message.getMessage("RejectedOrder.message"));
            case DELIVERED:
                throw new InvalidOperationException("order", message.getMessage("DeliveredOrder.message"));
            default:
                throw new InvalidOperationException("order", message.getMessage("UnknownStatus.message"));
        }
        return newOrderStatusCode;
    }

}
