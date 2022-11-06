package com.ccrt.onlineshop.io.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.ccrt.onlineshop.io.entity.OrderStatusEntity;

public interface OrderStatusRepository extends PagingAndSortingRepository<OrderStatusEntity, Long> {

}
