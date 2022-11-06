package com.ccrt.onlineshop.shared.dto;

import java.util.Date;
import java.util.Set;

import com.ccrt.onlineshop.enums.OrderStatus;
import com.ccrt.onlineshop.enums.PaymentStatus;
import com.ccrt.onlineshop.enums.PaymentType;

public class OrderDto {
  private String orderId;
  private Date creationTime;
  private AddressDto billingAddress;
  private AddressDto shippingAddress;
  private double totalProductCost;
  private double deliveryCost;
  private OrderStatus orderStatus;
  private PaymentType paymentType;
  private PaymentStatus paymentStatus;
  private Set<OrderItemDto> orderItems;
  private Set<OrderStatusDto> orderStatusTimeline;

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public Date getCreationTime() {
    return creationTime;
  }

  public void setCreationTime(Date creationTime) {
    this.creationTime = creationTime;
  }

  public AddressDto getBillingAddress() {
    return billingAddress;
  }

  public void setBillingAddress(AddressDto billingAddress) {
    this.billingAddress = billingAddress;
  }

  public AddressDto getShippingAddress() {
    return shippingAddress;
  }

  public void setShippingAddress(AddressDto shippingAddress) {
    this.shippingAddress = shippingAddress;
  }

  public double getTotalProductCost() {
    return totalProductCost;
  }

  public void setTotalProductCost(double totalProductCost) {
    this.totalProductCost = totalProductCost;
  }

  public double getDeliveryCost() {
    return deliveryCost;
  }

  public void setDeliveryCost(double deliveryCost) {
    this.deliveryCost = deliveryCost;
  }

  public OrderStatus getOrderStatus() {
    return orderStatus;
  }

  public void setOrderStatus(OrderStatus orderStatus) {
    this.orderStatus = orderStatus;
  }

  public PaymentType getPaymentType() {
    return paymentType;
  }

  public void setPaymentType(PaymentType paymentType) {
    this.paymentType = paymentType;
  }

  public PaymentStatus getPaymentStatus() {
    return paymentStatus;
  }

  public void setPaymentStatus(PaymentStatus paymentStatus) {
    this.paymentStatus = paymentStatus;
  }

  public Set<OrderItemDto> getOrderItems() {
    return orderItems;
  }

  public void setOrderItems(Set<OrderItemDto> orderItems) {
    this.orderItems = orderItems;
  }

  public Set<OrderStatusDto> getOrderStatusTimeline() {
    return orderStatusTimeline;
  }

  public void setOrderStatusTimeline(Set<OrderStatusDto> orderStatusTimeline) {
    this.orderStatusTimeline = orderStatusTimeline;
  }

}
