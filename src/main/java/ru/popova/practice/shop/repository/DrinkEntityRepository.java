package ru.popova.practice.shop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.popova.practice.shop.entity.CategoryEntity;
import ru.popova.practice.shop.entity.DrinkEntity;

import java.util.List;

public interface DrinkEntityRepository extends JpaRepository<DrinkEntity, Integer>, JpaSpecificationExecutor<DrinkEntity> {
    List<DrinkEntity> findAllByCategoriesContains(CategoryEntity category);
}
