package com.ccrt.onlineshop.io.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import com.ccrt.onlineshop.enums.OrderStatus;
import com.ccrt.onlineshop.enums.PaymentStatus;
import com.ccrt.onlineshop.enums.PaymentType;

@Entity
@Table(name = "order")
public class OrderEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(nullable = false, unique = true, length = 20)
  private String orderId;

  @ManyToOne
  @JoinColumn(nullable = false)
  private UserEntity user;

  @Temporal(TemporalType.TIMESTAMP)
  @CreationTimestamp
  private Date creationTime;

  @ManyToOne
  @JoinColumn(nullable = false)
  private AddressEntity billingAddress;

  @ManyToOne
  @JoinColumn(nullable = false)
  private AddressEntity shippingAddress;

  @Column(nullable = false)
  private double totalProductCost;

  @Column(nullable = false)
  private double deliveryCost;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private OrderStatus orderStatus;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private PaymentType paymentType;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private PaymentStatus paymentStatus;

  @Column(nullable = true)
  @Temporal(TemporalType.TIMESTAMP)
  private Date cancellationTime;

  @Column(nullable = true)
  private String reasonForCancellation;

  @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
  private Set<OrderItemEntity> orderItems;

  @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
  private Set<OrderStatusEntity> orderStatusTimeline;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public UserEntity getUser() {
    return user;
  }

  public void setUser(UserEntity user) {
    this.user = user;
  }

  public Date getCreationTime() {
    return creationTime;
  }

  public void setCreationTime(Date creationTime) {
    this.creationTime = creationTime;
  }

  public AddressEntity getBillingAddress() {
    return billingAddress;
  }

  public void setBillingAddress(AddressEntity billingAddress) {
    this.billingAddress = billingAddress;
  }

  public AddressEntity getShippingAddress() {
    return shippingAddress;
  }

  public void setShippingAddress(AddressEntity shippingAddress) {
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

  public Date getCancellationTime() {
    return cancellationTime;
  }

  public void setCancellationTime(Date cancellationTime) {
    this.cancellationTime = cancellationTime;
  }

  public String getReasonForCancellation() {
    return reasonForCancellation;
  }

  public void setReasonForCancellation(String reasonForCancellation) {
    this.reasonForCancellation = reasonForCancellation;
  }

  public Set<OrderItemEntity> getOrderItems() {
    return orderItems;
  }

  public void setOrderItems(Set<OrderItemEntity> orderItems) {
    this.orderItems = orderItems;
  }

  public Set<OrderStatusEntity> getOrderStatusTimeline() {
    return orderStatusTimeline;
  }

  public void setOrderStatusTimeline(Set<OrderStatusEntity> orderStatusTimeline) {
    this.orderStatusTimeline = orderStatusTimeline;
  }
}
