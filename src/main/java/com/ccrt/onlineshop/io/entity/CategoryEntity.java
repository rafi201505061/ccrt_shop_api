package com.ccrt.onlineshop.io.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "category")
public class CategoryEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(nullable = false, length = 20, unique = true)
  private String categoryId;

  @Column(nullable = false, length = 100, unique = true)
  private String title;

  @Column(nullable = false, length = 250)
  private String imageUrl;

  @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
  Set<SubCategoryEntity> subCategories = new HashSet<>();

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

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

  public Set<SubCategoryEntity> getSubCategories() {
    return subCategories;
  }

  public void setSubCategories(Set<SubCategoryEntity> subCategories) {
    this.subCategories = subCategories;
  }
}
