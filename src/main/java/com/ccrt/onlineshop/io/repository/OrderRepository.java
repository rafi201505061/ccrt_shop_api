package com.ccrt.onlineshop.io.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.ccrt.onlineshop.io.entity.OrderEntity;

public interface OrderRepository extends PagingAndSortingRepository<OrderEntity, Long> {

}
