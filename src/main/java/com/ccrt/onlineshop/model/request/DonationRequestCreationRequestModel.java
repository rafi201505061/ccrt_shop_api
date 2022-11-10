package com.ccrt.onlineshop.model.request;

public class DonationRequestCreationRequestModel {
  private String donorName;
  private String phoneNo;
  private String address;
  private String productTitle;
  private int numItems;

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

}
