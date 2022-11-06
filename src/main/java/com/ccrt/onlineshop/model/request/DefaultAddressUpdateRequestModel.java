package com.ccrt.onlineshop.model.request;

import com.ccrt.onlineshop.enums.DefaultAddressStatus;

public class DefaultAddressUpdateRequestModel {
  private DefaultAddressStatus defaultAddressStatus;
  private String addressId;

  public DefaultAddressStatus getDefaultAddressStatus() {
    return defaultAddressStatus;
  }

  public void setDefaultAddressStatus(DefaultAddressStatus defaultAddressStatus) {
    this.defaultAddressStatus = defaultAddressStatus;
  }

  public String getAddressId() {
    return addressId;
  }

  public void setAddressId(String addressId) {
    this.addressId = addressId;
  }
}
