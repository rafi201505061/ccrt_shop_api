package com.ccrt.onlineshop.model.request;

public class PromotedCategoryUpdateRequestModel {
  private int slotNo;
  private String subCategoryId;

  public int getSlotNo() {
    return slotNo;
  }

  public void setSlotNo(int slotNo) {
    this.slotNo = slotNo;
  }

  public String getSubCategoryId() {
    return subCategoryId;
  }

  public void setSubCategoryId(String subCategoryId) {
    this.subCategoryId = subCategoryId;
  }
}
