package com.ccrt.onlineshop.io.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.ccrt.onlineshop.io.entity.OrderItemEntity;

@Repository
public interface OrderItemRepository extends PagingAndSortingRepository<OrderItemEntity, Long> {

}
