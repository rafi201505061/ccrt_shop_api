package com.ccrt.onlineshop.service;

import java.util.List;

import com.ccrt.onlineshop.shared.dto.CategoryDto;
import com.ccrt.onlineshop.shared.dto.SubCategoryDto;

public interface CategoryService {
  CategoryDto createCategory(CategoryDto categoryDto);

  List<CategoryDto> retrieveAllCategories();

  SubCategoryDto addSubCategory(String categoryId, SubCategoryDto subCategoryDto);

  List<SubCategoryDto> retrieveAllSubCategories(String categoryId);
}
