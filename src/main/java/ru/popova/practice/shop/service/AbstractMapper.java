package ru.popova.practice.shop.service;

public interface AbstractMapper<E, D> {
    D toDto(E entity);
}
