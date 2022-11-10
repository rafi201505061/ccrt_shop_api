package com.ccrt.onlineshop.model.request;

import com.ccrt.onlineshop.enums.DonationRequestStatus;

public class DonationStatusUpdateRequestModel {
  private DonationRequestStatus status;

  public DonationRequestStatus getStatus() {
    return status;
  }

  public void setStatus(DonationRequestStatus status) {
    this.status = status;
  }
}
