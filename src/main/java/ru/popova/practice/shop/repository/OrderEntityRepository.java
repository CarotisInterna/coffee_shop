package ru.popova.practice.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.popova.practice.shop.entity.AppUserEntity;
import ru.popova.practice.shop.entity.OrderEntity;
import ru.popova.practice.shop.entity.OrderStatusEntity;

public interface OrderEntityRepository extends JpaRepository<OrderEntity, Integer> {
    OrderEntity findOrderEntityByAppUserAndOrderStatus(AppUserEntity appUserEntity, OrderStatusEntity orderStatusEntity);
}
