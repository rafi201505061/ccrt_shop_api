package com.ccrt.onlineshop.shared.dto;

import org.springframework.web.multipart.MultipartFile;

import com.ccrt.onlineshop.enums.DonationRequestStatus;

public class DonationRequestDto {
  private String requestId;
  private String donorName;
  private String phoneNo;
  private String address;
  private String productTitle;
  private int numItems;
  private DonationRequestStatus status;
  private MultipartFile image;
  private String imageUrl;

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

  public MultipartFile getImage() {
    return image;
  }

  public void setImage(MultipartFile image) {
    this.image = image;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  };
}
