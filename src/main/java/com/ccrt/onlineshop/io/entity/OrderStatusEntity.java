package com.ccrt.onlineshop.io.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import com.ccrt.onlineshop.enums.OrderStatus;

@Entity
@Table(name = "order_status")
public class OrderStatusEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @ManyToOne
  @JoinColumn(nullable = false)
  private OrderEntity order;

  @Enumerated(EnumType.STRING)
  private OrderStatus orderStatus;

  @Temporal(TemporalType.TIMESTAMP)
  @CreationTimestamp
  private Date creationTime;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public OrderEntity getOrder() {
    return order;
  }

  public void setOrder(OrderEntity order) {
    this.order = order;
  }

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
