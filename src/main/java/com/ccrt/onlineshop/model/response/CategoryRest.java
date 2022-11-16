package com.ccrt.onlineshop.model.response;

import java.util.List;

public class CategoryRest {
  private String categoryId;
  private String title;
  private String imageUrl;
  private List<SubCategoryRest> SubCategories;

  public String getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(String categoryId) {
    this.categoryId = categoryId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public List<SubCategoryRest> getSubCategories() {
    return SubCategories;
  }

  public void setSubCategories(List<SubCategoryRest> subCategories) {
    SubCategories = subCategories;
  }
}
