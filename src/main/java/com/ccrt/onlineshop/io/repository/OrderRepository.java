package com.ccrt.onlineshop.io.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.ccrt.onlineshop.enums.OrderStatus;
import com.ccrt.onlineshop.io.entity.OrderEntity;

@Repository
public interface OrderRepository extends PagingAndSortingRepository<OrderEntity, Long> {
  OrderEntity findByOrderId(String orderId);

  Page<OrderEntity> findAllByUser_UserId(String userId, Pageable pageable);

  Page<OrderEntity> findAllByOrderStatus(OrderStatus orderStatus, Pageable pageable);

  Page<OrderEntity> findAll(Pageable pageable);

}
