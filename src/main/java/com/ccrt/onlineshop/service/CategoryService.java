package com.ccrt.onlineshop.service;

import java.util.List;

import com.ccrt.onlineshop.shared.dto.CategoryDto;
import com.ccrt.onlineshop.shared.dto.PromotedCategoryDto;
import com.ccrt.onlineshop.shared.dto.SubCategoryDto;

public interface CategoryService {
  CategoryDto createCategory(CategoryDto categoryDto);

  CategoryDto updateCategory(String categoryId, CategoryDto categoryDto);

  List<CategoryDto> retrieveAllCategories();

  SubCategoryDto addSubCategory(String categoryId, SubCategoryDto subCategoryDto);

  SubCategoryDto updateSubCategory(String categoryId, String subCategoryId, SubCategoryDto subCategoryDto);

  List<SubCategoryDto> retrieveAllSubCategories(String categoryId);

  PromotedCategoryDto changePromotedCategory(PromotedCategoryDto promotedCategoryDto);

  List<PromotedCategoryDto> retrievePromotedCategories();

  SubCategoryDto retrieveSubCategoryBySubCategoryId(String subCategoryId);

  List<CategoryDto> retrieveCategoryList();
}
