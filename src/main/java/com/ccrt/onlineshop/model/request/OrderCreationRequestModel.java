package com.ccrt.onlineshop.model.request;

import java.util.List;

import com.ccrt.onlineshop.enums.DeliveryPlaceType;
import com.ccrt.onlineshop.enums.PaymentStatus;
import com.ccrt.onlineshop.enums.PaymentType;

public class OrderCreationRequestModel {
  private String billingAddressId;
  private String shippingAddressId;
  private PaymentType paymentType;
  private PaymentStatus paymentStatus;
  private List<OrderItemCreationRequestModel> orderItems;
  private DeliveryPlaceType place;

  public String getBillingAddressId() {
    return billingAddressId;
  }

  public void setBillingAddressId(String billingAddressId) {
    this.billingAddressId = billingAddressId;
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

  public List<OrderItemCreationRequestModel> getOrderItems() {
    return orderItems;
  }

  public void setOrderItems(List<OrderItemCreationRequestModel> orderItems) {
    this.orderItems = orderItems;
  }

  public DeliveryPlaceType getPlace() {
    return place;
  }

  public void setPlace(DeliveryPlaceType place) {
    this.place = place;
  }

  public String getShippingAddressId() {
    return shippingAddressId;
  }

  public void setShippingAddressId(String shippingAddressId) {
    this.shippingAddressId = shippingAddressId;
  }

}
