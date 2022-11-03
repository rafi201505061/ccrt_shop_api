package com.ccrt.onlineshop.model.response;

import com.ccrt.onlineshop.enums.UsageStatus;

public class ProductRest {
  private String productId;
  private String title;
  private String description;
  private double price;
  private String imageUrl;
  private long totalEntities;
  private long remainingEntities;
  private UsageStatus usageStatus;

  public String getProductId() {
    return productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public long getTotalEntities() {
    return totalEntities;
  }

  public void setTotalEntities(long totalEntities) {
    this.totalEntities = totalEntities;
  }

  public long getRemainingEntities() {
    return remainingEntities;
  }

  public void setRemainingEntities(long remainingEntities) {
    this.remainingEntities = remainingEntities;
  }

  public UsageStatus getUsageStatus() {
    return usageStatus;
  }

  public void setUsageStatus(UsageStatus usageStatus) {
    this.usageStatus = usageStatus;
  }
}
