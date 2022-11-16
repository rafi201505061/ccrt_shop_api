package com.ccrt.onlineshop.shared.dto;

public class CampaignProductDto {
  private ProductDto product;
  private String productId;
  private double priceReductionPercentage;

  public ProductDto getProduct() {
    return product;
  }

  public void setProduct(ProductDto product) {
    this.product = product;
  }

  public double getPriceReductionPercentage() {
    return priceReductionPercentage;
  }

  public void setPriceReductionPercentage(double priceReductionPercentage) {
    this.priceReductionPercentage = priceReductionPercentage;
  }

  public String getProductId() {
    return productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }
}
