package com.ccrt.onlineshop.io.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ccrt.onlineshop.io.entity.PromotedCategoryEntity;

public interface PromotedCategoryRepository extends CrudRepository<PromotedCategoryEntity, Long> {
  PromotedCategoryEntity findBySlotNo(int slotNo);

  List<PromotedCategoryEntity> findAll();
}
