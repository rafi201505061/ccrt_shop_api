package com.ccrt.onlineshop.model.request;

import com.ccrt.onlineshop.enums.OrderStatus;

public class OrderStatusUpdateRequestModel {
  private OrderStatus orderStatus;

  public OrderStatus getOrderStatus() {
    return orderStatus;
  }

  public void setOrderStatus(OrderStatus orderStatus) {
    this.orderStatus = orderStatus;
  }
}
