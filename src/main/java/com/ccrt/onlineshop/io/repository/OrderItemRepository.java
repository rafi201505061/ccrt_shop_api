package com.ccrt.onlineshop.io.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.ccrt.onlineshop.io.entity.OrderItemEntity;

public interface OrderItemRepository extends PagingAndSortingRepository<OrderItemEntity, Long> {

}
