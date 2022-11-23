package com.ccrt.onlineshop.io.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import com.ccrt.onlineshop.enums.DonationRequestStatus;

@Entity
@Table(name = "donation_requests")
public class DonationRequestEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(nullable = false, length = 20, unique = true)
  private String requestId;

  @Column(nullable = false, length = 200)
  private String donorName;

  @Column(nullable = false, length = 20)
  private String phoneNo;

  @Column(nullable = false, length = 500)
  private String address;

  @Column(nullable = false, length = 250)
  private String productTitle;

  @Column(nullable = false)
  private int numItems;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private DonationRequestStatus status = DonationRequestStatus.PENDING;

  @Column(nullable = true, length = 250)
  private String imageUrl;

  @Temporal(TemporalType.TIMESTAMP)
  @CreationTimestamp
  private Date creationTime;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getRequestId() {
    return requestId;
  }

  public void setRequestId(String requestId) {
    this.requestId = requestId;
  }

  public String getDonorName() {
    return donorName;
  }

  public void setDonorName(String donorName) {
    this.donorName = donorName;
  }

  public String getPhoneNo() {
    return phoneNo;
  }

  public void setPhoneNo(String phoneNo) {
    this.phoneNo = phoneNo;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getProductTitle() {
    return productTitle;
  }

  public void setProductTitle(String productTitle) {
    this.productTitle = productTitle;
  }

  public int getNumItems() {
    return numItems;
  }

  public void setNumItems(int numItems) {
    this.numItems = numItems;
  }

  public DonationRequestStatus getStatus() {
    return status;
  }

  public void setStatus(DonationRequestStatus status) {
    this.status = status;
  }

  public Date getCreationTime() {
    return creationTime;
  }

  public void setCreationTime(Date creationTime) {
    this.creationTime = creationTime;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

}
