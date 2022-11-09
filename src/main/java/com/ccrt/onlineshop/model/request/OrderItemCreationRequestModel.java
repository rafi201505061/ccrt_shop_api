package com.ccrt.onlineshop.model.request;

public class OrderItemCreationRequestModel {
  private String productId;
  private long numItems;
  private double unitPrice;

  public String getProductId() {
    return productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }

  public long getNumItems() {
    return numItems;
  }

  public void setNumItems(long numItems) {
    this.numItems = numItems;
  }

  public double getUnitPrice() {
    return unitPrice;
  }

  public void setUnitPrice(double unitPrice) {
    this.unitPrice = unitPrice;
  }
}
