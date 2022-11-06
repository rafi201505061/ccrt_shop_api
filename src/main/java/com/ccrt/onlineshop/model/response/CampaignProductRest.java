package com.ccrt.onlineshop.model.response;

public class CampaignProductRest {
  private ProductRest product;
  private double priceReductionPercentage;

  public ProductRest getProduct() {
    return product;
  }

  public void setProduct(ProductRest product) {
    this.product = product;
  }

  public double getPriceReductionPercentage() {
    return priceReductionPercentage;
  }

  public void setPriceReductionPercentage(double priceReductionPercentage) {
    this.priceReductionPercentage = priceReductionPercentage;
  }
}
