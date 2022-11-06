package com.ccrt.onlineshop.io.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "campaign_product")
public class CampaignProductEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @ManyToOne
  @JoinColumn(nullable = false)
  private CampaignEntity campaign;

  @ManyToOne
  @JoinColumn(nullable = false)
  private ProductEntity product;

  @Column(nullable = false)
  private double priceReductionPercentage;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public CampaignEntity getCampaign() {
    return campaign;
  }

  public void setCampaign(CampaignEntity campaign) {
    this.campaign = campaign;
  }

  public ProductEntity getProduct() {
    return product;
  }

  public void setProduct(ProductEntity product) {
    this.product = product;
  }

  public double getPriceReductionPercentage() {
    return priceReductionPercentage;
  }

  public void setPriceReductionPercentage(double priceReductionPercentage) {
    this.priceReductionPercentage = priceReductionPercentage;
  }
}
