package com.ccrt.onlineshop.model.request;

import com.ccrt.onlineshop.enums.CoverType;

public class CoverCreationRequestModel {
  private CoverType type;
  private String itemId;
  private String link;

  public CoverType getType() {
    return type;
  }

  public void setType(CoverType type) {
    this.type = type;
  }

  public String getItemId() {
    return itemId;
  }

  public void setItemId(String itemId) {
    this.itemId = itemId;
  }

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }
}
