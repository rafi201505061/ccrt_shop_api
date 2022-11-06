package com.ccrt.onlineshop.model.response;

import java.util.Date;
import java.util.List;

import com.ccrt.onlineshop.enums.OrderStatus;
import com.ccrt.onlineshop.enums.PaymentStatus;
import com.ccrt.onlineshop.enums.PaymentType;

public class OrderRest {
  private String orderId;
  private Date creationTime;
  private AddressRest billingAddress;
  private AddressRest shippingAddress;
  private double totalProductCost;
  private double deliveryCost;
  private OrderStatus orderStatus;
  private PaymentType paymentType;
  private PaymentStatus paymentStatus;
  private List<OrderItemRest> orderItems;
  private List<OrderStatusRest> orderStatusTimeline;

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

  public AddressRest getBillingAddress() {
    return billingAddress;
  }

  public void setBillingAddress(AddressRest billingAddress) {
    this.billingAddress = billingAddress;
  }

  public AddressRest getShippingAddress() {
    return shippingAddress;
  }

  public void setShippingAddress(AddressRest shippingAddress) {
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

  public List<OrderItemRest> getOrderItems() {
    return orderItems;
  }

  public void setOrderItems(List<OrderItemRest> orderItems) {
    this.orderItems = orderItems;
  }

  public List<OrderStatusRest> getOrderStatusTimeline() {
    return orderStatusTimeline;
  }

  public void setOrderStatusTimeline(List<OrderStatusRest> orderStatusTimeline) {
    this.orderStatusTimeline = orderStatusTimeline;
  }
}
