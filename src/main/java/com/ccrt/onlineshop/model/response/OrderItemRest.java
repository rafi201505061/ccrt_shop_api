package com.ccrt.onlineshop.model.response;

public class OrderItemRest {
  private ProductRest product;
  private double unitPrice;
  private long numItems;
  private double totalCost;

  public ProductRest getProduct() {
    return product;
  }

  public void setProduct(ProductRest product) {
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
}
