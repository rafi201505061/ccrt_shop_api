package com.ccrt.onlineshop.io.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.ccrt.onlineshop.io.entity.CategoryEntity;

@Repository
public interface CategoryRepository extends PagingAndSortingRepository<CategoryEntity, Long> {
  List<CategoryEntity> findAllByOrderByTitleDesc();

  CategoryEntity findByCategoryId(String categoryId);
}
