package com.ccrt.onlineshop.model.response;

import com.ccrt.onlineshop.enums.CoverType;

public class CoverRest {
  private long id;
  private CoverType type;
  private String itemId;
  private String imageUrl;
  private String link;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

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

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }

}
