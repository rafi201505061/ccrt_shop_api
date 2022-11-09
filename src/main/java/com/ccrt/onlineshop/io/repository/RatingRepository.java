package com.ccrt.onlineshop.io.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.ccrt.onlineshop.io.entity.RatingEntity;

@Repository
public interface RatingRepository extends PagingAndSortingRepository<RatingEntity, Long> {
  RatingEntity findByUser_UserIdAndProduct_ProductId(String userId, String productId);
}
