package com.ccrt.onlineshop.service;

import java.util.List;

import com.ccrt.onlineshop.enums.OrderStatus;
import com.ccrt.onlineshop.shared.dto.OrderDto;

public interface OrderService {
  OrderDto createOrder(String userId, OrderDto orderDto);

  OrderDto retrieveOrder(String orderId);

  List<OrderDto> retrieveOrders(String userId, int page, int limit);

  List<OrderDto> retrieveOrders(OrderStatus orderStatus, int page, int limit);

  OrderDto updateStatus(String orderId, OrderStatus orderStatus);

  void cancelOrder(String userId, String orderId);

}
