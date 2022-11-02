package com.ccrt.onlineshop.model.response;

public class PromotedCategoryRest {
  private int slotNo;
  private SubCategoryRest subCategory;

  public int getSlotNo() {
    return slotNo;
  }

  public void setSlotNo(int slotNo) {
    this.slotNo = slotNo;
  }

  public SubCategoryRest getSubCategory() {
    return subCategory;
  }

  public void setSubCategory(SubCategoryRest subCategory) {
    this.subCategory = subCategory;
  }
}
