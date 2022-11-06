package com.ccrt.onlineshop.io.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "sub_category")
public class SubCategoryEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(nullable = false, unique = true, length = 20)
  private String subCategoryId;

  @Column(nullable = false, length = 150)
  private String title;

  @Column(nullable = false, length = 250)
  private String imageUrl;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(nullable = false)
  private CategoryEntity category;

  @OneToMany(mappedBy = "subCategory", fetch = FetchType.LAZY)
  private Set<ProductEntity> products = new HashSet<>();

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getSubCategoryId() {
    return subCategoryId;
  }

  public void setSubCategoryId(String subCategoryId) {
    this.subCategoryId = subCategoryId;
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

  public CategoryEntity getCategory() {
    return category;
  }

  public void setCategory(CategoryEntity category) {
    this.category = category;
  }

  public Set<ProductEntity> getProducts() {
    return products;
  }

  public void setProducts(Set<ProductEntity> products) {
    this.products = products;
  }
}
