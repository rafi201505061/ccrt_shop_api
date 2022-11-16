package com.ccrt.onlineshop.model.request;

public class CampaignProductCreationRequestModel {
  private String productId;
  private double priceReductionPercentage;

  public String getProductId() {
    return productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }

  public double getPriceReductionPercentage() {
    return priceReductionPercentage;
  }

  public void setPriceReductionPercentage(double priceReductionPercentage) {
    this.priceReductionPercentage = priceReductionPercentage;
  }
}
