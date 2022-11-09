package com.ccrt.onlineshop.io.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "order_item")
public class OrderItemEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(nullable = false)
  private ProductEntity product;

  @ManyToOne
  @JoinColumn(nullable = false)
  private OrderEntity order;

  @Column(nullable = false)
  private double unitPrice;

  @Column(nullable = false)
  private long numItems;

  @Column(nullable = false)
  private double totalCost;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public ProductEntity getProduct() {
    return product;
  }

  public void setProduct(ProductEntity product) {
    this.product = product;
  }

  public OrderEntity getOrder() {
    return order;
  }

  public void setOrder(OrderEntity order) {
    this.order = order;
  }

  public double getUnitPrice() {
    return unitPrice;
  }

  public void setUnitPrice(double unitPrice) {
    this.unitPrice = unitPrice;
  }

  public long getNumItems() {
    return numItems;
  }

  public void setNumItems(long numItems) {
    this.numItems = numItems;
  }

  public double getTotalCost() {
    return totalCost;
  }

  public void setTotalCost(double totalCost) {
    this.totalCost = totalCost;
  }

}
