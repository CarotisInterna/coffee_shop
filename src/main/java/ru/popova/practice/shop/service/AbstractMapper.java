package ru.popova.practice.shop.service;

public interface AbstractMapper<E, D> {
    /**
     * маппинг сущности в дто
     * @param entity сущность
     * @return дто
     */
    D toDto(E entity);
}
