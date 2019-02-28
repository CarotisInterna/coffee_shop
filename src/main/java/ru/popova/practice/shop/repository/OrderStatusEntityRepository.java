package ru.popova.practice.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.popova.practice.shop.entity.OrderStatusEntity;
import ru.popova.practice.shop.entity.code.OrderStatusCode;

public interface OrderStatusEntityRepository extends JpaRepository<OrderStatusEntity, OrderStatusCode> {

    OrderStatusEntity findOrderStatusEntityByCode(OrderStatusCode code);

    OrderStatusEntity findOrderStatusEntityByName(String name);
}
