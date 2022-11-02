package com.ccrt.onlineshop.io.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "promoted_category")
public class PromotedCategoryEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(unique = true, nullable = false)
  private int slotNo;

  @OneToOne(fetch = FetchType.EAGER)
  private SubCategoryEntity subCategory;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public int getSlotNo() {
    return slotNo;
  }

  public void setSlotNo(int slotNo) {
    this.slotNo = slotNo;
  }

  public SubCategoryEntity getSubCategory() {
    return subCategory;
  }

  public void setSubCategory(SubCategoryEntity subCategory) {
    this.subCategory = subCategory;
  }
}
