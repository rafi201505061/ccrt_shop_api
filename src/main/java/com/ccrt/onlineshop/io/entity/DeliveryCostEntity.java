package com.ccrt.onlineshop.io.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ccrt.onlineshop.enums.DeliveryPlaceType;

@Entity
@Table(name = "delivery_cost")
public class DeliveryCostEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(nullable = false, unique = true)
  @Enumerated(EnumType.STRING)
  private DeliveryPlaceType place;

  @Column(nullable = false)
  private double cost;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public double getCost() {
    return cost;
  }

  public void setCost(double cost) {
    this.cost = cost;
  }

  public DeliveryPlaceType getPlace() {
    return place;
  }

  public void setPlace(DeliveryPlaceType place) {
    this.place = place;
  }
}
