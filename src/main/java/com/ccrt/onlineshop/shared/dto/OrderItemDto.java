package com.ccrt.onlineshop.shared.dto;

public class OrderItemDto {
  private String productId;
  private ProductDto product;
  private double unitPrice;
  private long numItems;
  private double totalCost;

  public ProductDto getProduct() {
    return product;
  }

  public void setProduct(ProductDto product) {
    this.product = product;
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

  public String getProductId() {
    return productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }

}
