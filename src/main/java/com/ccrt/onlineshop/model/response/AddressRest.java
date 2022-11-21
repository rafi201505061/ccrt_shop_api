package com.ccrt.onlineshop.model.response;

import com.ccrt.onlineshop.enums.AddressType;
import com.ccrt.onlineshop.enums.DefaultAddressStatus;

public class AddressRest {
  private String addressId;
  private String fullName;
  private String phoneNo;
  private String province;
  private String city;
  private String village;
  private String details;
  private String landmark;
  private AddressType addressType;
  private DefaultAddressStatus defaultAddressStatus;

  public String getAddressId() {
    return addressId;
  }

  public void setAddressId(String addressId) {
    this.addressId = addressId;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getPhoneNo() {
    return phoneNo;
  }

  public void setPhoneNo(String phoneNo) {
    this.phoneNo = phoneNo;
  }

  public String getProvince() {
    return province;
  }

  public void setProvince(String province) {
    this.province = province;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getVillage() {
    return village;
  }

  public void setVillage(String village) {
    this.village = village;
  }

  public String getLandmark() {
    return landmark;
  }

  public void setLandmark(String landmark) {
    this.landmark = landmark;
  }

  public AddressType getAddressType() {
    return addressType;
  }

  public void setAddressType(AddressType addressType) {
    this.addressType = addressType;
  }

  public DefaultAddressStatus getDefaultAddressStatus() {
    return defaultAddressStatus;
  }

  public void setDefaultAddressStatus(DefaultAddressStatus defaultAddressStatus) {
    this.defaultAddressStatus = defaultAddressStatus;
  }

  public String getDetails() {
    return details;
  }

  public void setDetails(String details) {
    this.details = details;
  }
}
