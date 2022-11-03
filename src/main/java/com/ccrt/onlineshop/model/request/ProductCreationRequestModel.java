package com.ccrt.onlineshop.model.request;

import com.ccrt.onlineshop.enums.UsageStatus;

public class ProductCreationRequestModel {
  private String title;
  private String description;
  private double price;
  private UsageStatus usageStatus;
  private long totalEntities;
  private String subCategoryId;

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

  public UsageStatus getUsageStatus() {
    return usageStatus;
  }

  public void setUsageStatus(UsageStatus usageStatus) {
    this.usageStatus = usageStatus;
  }

  public long getTotalEntities() {
    return totalEntities;
  }

  public void setTotalEntities(long totalEntities) {
    this.totalEntities = totalEntities;
  }

  public String getSubCategoryId() {
    return subCategoryId;
  }

  public void setSubCategoryId(String subCategoryId) {
    this.subCategoryId = subCategoryId;
  }

  @Override
  public String toString() {
    return "ProductCreationRequestModel [title=" + title + ", description=" + description + ", price=" + price
        + ", usageStatus=" + usageStatus + ", totalEntities=" + totalEntities + ", subCategoryId=" + subCategoryId
        + "]";
  }
}
