package com.ccrt.onlineshop.shared.dto;

import java.util.Date;

import com.ccrt.onlineshop.enums.OrderStatus;

public class OrderStatusDto {
  private OrderStatus orderStatus;
  private Date creationTime;

  public OrderStatus getOrderStatus() {
    return orderStatus;
  }

  public void setOrderStatus(OrderStatus orderStatus) {
    this.orderStatus = orderStatus;
  }

  public Date getCreationTime() {
    return creationTime;
  }

  public void setCreationTime(Date creationTime) {
    this.creationTime = creationTime;
  }

}
