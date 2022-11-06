package com.ccrt.onlineshop.io.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import com.ccrt.onlineshop.enums.UsageStatus;

@Entity
@Table(name = "product")
public class ProductEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(nullable = false, unique = true, length = 20)
  private String productId;

  @Column(nullable = false, length = 250)
  private String title;

  @Column(nullable = true, length = 5000)
  private String description;

  @Column(nullable = false)
  private double prevPrice = 0;

  @Column(nullable = false)
  private double price;

  @Column(nullable = false, length = 250)
  private String imageUrl;

  @Column(nullable = false)
  private long totalEntities;

  @Column(nullable = false)
  private long remainingEntities;

  @Temporal(TemporalType.TIMESTAMP)
  @CreationTimestamp
  private Date creationTime;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private UsageStatus usageStatus;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(nullable = false)
  private UserEntity uploader;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(nullable = false)
  private SubCategoryEntity subCategory;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

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

  public Date getCreationTime() {
    return creationTime;
  }

  public void setCreationTime(Date creationTime) {
    this.creationTime = creationTime;
  }

  public UserEntity getUploader() {
    return uploader;
  }

  public void setUploader(UserEntity uploader) {
    this.uploader = uploader;
  }

  public SubCategoryEntity getSubCategory() {
    return subCategory;
  }

  public void setSubCategory(SubCategoryEntity subCategory) {
    this.subCategory = subCategory;
  }

  public UsageStatus getUsageStatus() {
    return usageStatus;
  }

  public void setUsageStatus(UsageStatus usageStatus) {
    this.usageStatus = usageStatus;
  }

  public double getPrevPrice() {
    return prevPrice;
  }

  public void setPrevPrice(double prevPrice) {
    this.prevPrice = prevPrice;
  }

}
