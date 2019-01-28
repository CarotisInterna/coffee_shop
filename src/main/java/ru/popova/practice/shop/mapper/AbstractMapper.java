package ru.popova.practice.shop.mapper;

public interface AbstractMapper<E, D> {
    /**
     * маппинг сущности в дто
     * @param entity сущность
     * @return дто
     */
    D toDto(E entity);

    /**
     * маппинг дто в сущность
     * @param dto дто
     * @return сущность
     */
    E toEntity(D dto);
}
