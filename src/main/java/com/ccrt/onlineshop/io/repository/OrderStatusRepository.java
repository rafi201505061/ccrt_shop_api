package com.ccrt.onlineshop.io.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.ccrt.onlineshop.io.entity.OrderStatusEntity;

@Repository
public interface OrderStatusRepository extends PagingAndSortingRepository<OrderStatusEntity, Long> {

}
