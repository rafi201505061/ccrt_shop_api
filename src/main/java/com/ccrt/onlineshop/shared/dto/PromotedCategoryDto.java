package com.ccrt.onlineshop.shared.dto;

public class PromotedCategoryDto {
  private int slotNo;
  private SubCategoryDto subCategory;
  private String subCategoryId;

  public int getSlotNo() {
    return slotNo;
  }

  public void setSlotNo(int slotNo) {
    this.slotNo = slotNo;
  }

  public SubCategoryDto getSubCategory() {
    return subCategory;
  }

  public void setSubCategory(SubCategoryDto subCategory) {
    this.subCategory = subCategory;
  }

  public String getSubCategoryId() {
    return subCategoryId;
  }

  public void setSubCategoryId(String subCategoryId) {
    this.subCategoryId = subCategoryId;
  }
}
