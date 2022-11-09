package com.ccrt.onlineshop.shared.dto;

import org.springframework.web.multipart.MultipartFile;

import com.ccrt.onlineshop.enums.UsageStatus;

public class ProductDto {
  private String productId;
  private String title;
  private String description;
  private double prevPrice;
  private double price;
  private MultipartFile image;
  private String imageUrl;
  private long totalEntities;
  private long remainingEntities;
  private String subCategoryId;
  private UsageStatus usageStatus;
  private String uploaderUserId;
  private double averageRating;
  private double rating;
  private String raterUserId;

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

  public MultipartFile getImage() {
    return image;
  }

  public void setImage(MultipartFile image) {
    this.image = image;
  }

  public String getSubCategoryId() {
    return subCategoryId;
  }

  public void setSubCategoryId(String subCategoryId) {
    this.subCategoryId = subCategoryId;
  }

  public UsageStatus getUsageStatus() {
    return usageStatus;
  }

  public void setUsageStatus(UsageStatus usageStatus) {
    this.usageStatus = usageStatus;
  }

  public String getUploaderUserId() {
    return uploaderUserId;
  }

  public void setUploaderUserId(String uploaderUserId) {
    this.uploaderUserId = uploaderUserId;
  }

  public double getPrevPrice() {
    return prevPrice;
  }

  public void setPrevPrice(double prevPrice) {
    this.prevPrice = prevPrice;
  }

  public double getAverageRating() {
    return averageRating;
  }

  public void setAverageRating(double averageRating) {
    this.averageRating = averageRating;
  }

  public double getRating() {
    return rating;
  }

  public void setRating(double rating) {
    this.rating = rating;
  }

  public String getRaterUserId() {
    return raterUserId;
  }

  public void setRaterUserId(String raterUserId) {
    this.raterUserId = raterUserId;
  }

}
