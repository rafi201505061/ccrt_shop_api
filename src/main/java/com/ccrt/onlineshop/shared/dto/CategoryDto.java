package com.ccrt.onlineshop.shared.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class CategoryDto {
  private String categoryId;
  private String title;
  private String imageUrl;
  private MultipartFile image;
  private List<SubCategoryDto> subCategories;

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

  public MultipartFile getImage() {
    return image;
  }

  public void setImage(MultipartFile image) {
    this.image = image;
  }

  public List<SubCategoryDto> getSubCategories() {
    return subCategories;
  }

  public void setSubCategories(List<SubCategoryDto> subCategories) {
    this.subCategories = subCategories;
  }
}
