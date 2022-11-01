package com.ccrt.onlineshop.service;

import java.util.List;

import com.ccrt.onlineshop.shared.dto.CategoryDto;

public interface CategoryService {
  CategoryDto createCategory(CategoryDto categoryDto);

  List<CategoryDto> retrieveAllCategories();
}
