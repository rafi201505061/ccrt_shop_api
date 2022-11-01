package com.ccrt.onlineshop.io.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.ccrt.onlineshop.io.entity.CategoryEntity;

public interface CategoryRepository extends PagingAndSortingRepository<CategoryEntity, Long> {
  List<CategoryEntity> findAllByOrderByTitleDesc();
}
