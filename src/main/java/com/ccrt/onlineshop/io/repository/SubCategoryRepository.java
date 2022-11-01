package com.ccrt.onlineshop.io.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.ccrt.onlineshop.io.entity.SubCategoryEntity;

@Repository
public interface SubCategoryRepository extends PagingAndSortingRepository<SubCategoryEntity, Long> {
  List<SubCategoryEntity> findAllByCategory_CategoryIdOrderByTitleDesc(String categoryId);
}
