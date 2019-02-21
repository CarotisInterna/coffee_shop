package ru.popova.practice.shop.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.popova.practice.shop.dto.OrderDto;
import ru.popova.practice.shop.entity.OrderEntity;
import ru.popova.practice.shop.entity.code.OrderStatusCode;
import ru.popova.practice.shop.repository.AppUserEntityRepository;
import ru.popova.practice.shop.repository.OrderStatusEntityRepository;

import java.util.stream.Collectors;

@Component
public class OrderMapper implements AbstractMapper<OrderEntity, OrderDto> {

    private OrderStatusEntityRepository orderStatusEntityRepository;
    private DrinkOrderMapper drinkOrderMapper;
    private AppUserEntityRepository appUserEntityRepository;

    @Autowired
    public OrderMapper(OrderStatusEntityRepository orderStatusEntityRepository, DrinkOrderMapper drinkOrderMapper, AppUserEntityRepository appUserEntityRepository) {
        this.orderStatusEntityRepository = orderStatusEntityRepository;
        this.drinkOrderMapper = drinkOrderMapper;
        this.appUserEntityRepository = appUserEntityRepository;
    }

    @Override
    public OrderDto toDto(OrderEntity entity) {
        if (entity == null) {
            return null;
        } else {
            OrderDto orderDto = new OrderDto();
            orderDto.setId(entity.getId());
            orderDto.setOrderStatus(entity.getOrderStatus().getName());
            orderDto.setAppUser(entity.getAppUser().getUsername());
            orderDto.setTotal(entity.getTotal());
            orderDto.setAddress(entity.getAddress());
            orderDto.setDate(entity.getDate());
            orderDto.setDrinks(entity.getDrinks()
                    .stream()
                    .map(drink -> drinkOrderMapper.toDto(drink))
                    .collect(Collectors.toList()));
            return orderDto;
        }
    }

    @Override
    public OrderEntity toEntity(OrderDto dto) {
        if (dto == null) {
            return null;
        } else {
            OrderEntity orderEntity = new OrderEntity();

            String orderStatus = dto.getOrderStatus();
            String username = dto.getAppUser();
            orderEntity.setOrderStatus(orderStatusEntityRepository.findOrderStatusEntityByOrderStatusCode(OrderStatusCode.valueOf(orderStatus)));
            orderEntity.setAppUser(appUserEntityRepository.findAppUserEntityByUsername(username));
            orderEntity.setTotal(dto.getTotal());
            orderEntity.setAddress(dto.getAddress());
            orderEntity.setDate(dto.getDate());
            orderEntity.setDrinks(dto.getDrinks()
                    .stream()
                    .map(drink -> drinkOrderMapper.toEntity(drink))
                    .collect(Collectors.toList()));
            return orderEntity;
        }
    }
}
