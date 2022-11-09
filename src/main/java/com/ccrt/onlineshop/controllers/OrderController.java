package com.ccrt.onlineshop.controllers;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ccrt.onlineshop.enums.OrderStatus;
import com.ccrt.onlineshop.model.request.OrderStatusUpdateRequestModel;
import com.ccrt.onlineshop.model.response.OrderRest;
import com.ccrt.onlineshop.service.OrderService;
import com.ccrt.onlineshop.shared.dto.OrderDto;

@RestController
@RequestMapping("orders")
public class OrderController {

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private OrderService orderService;

  @GetMapping
  public List<OrderRest> retrieveOrders(
      @RequestParam(name = "page", required = false, defaultValue = "0") int page,
      @RequestParam(name = "limit", required = false, defaultValue = "15") int limit,
      @RequestParam(name = "status", required = true, defaultValue = "ALL") OrderStatus orderStatus) {
    List<OrderDto> orderDtos = orderService.retrieveOrders(orderStatus, page, limit);
    List<OrderRest> orderRests = new ArrayList<>();
    for (OrderDto orderDto : orderDtos) {
      orderRests.add(modelMapper.map(orderDto, OrderRest.class));
    }
    return orderRests;

  }

  @GetMapping("/{orderId}")
  public OrderRest getOrderDetails(@PathVariable String orderId) {
    OrderDto orderDto = orderService.retrieveOrder(orderId);
    return modelMapper.map(orderDto, OrderRest.class);
  }

  @PutMapping("/{orderId}/status")
  public OrderRest updateOrderStatus(@PathVariable String orderId,
      @RequestBody OrderStatusUpdateRequestModel orderStatusUpdateRequestModel) {
    OrderDto orderDto = orderService.updateStatus(orderId, orderStatusUpdateRequestModel.getOrderStatus());
    return modelMapper.map(orderDto, OrderRest.class);
  }
}
