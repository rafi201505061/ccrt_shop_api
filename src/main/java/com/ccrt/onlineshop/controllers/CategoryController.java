package com.ccrt.onlineshop.controllers;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ccrt.onlineshop.model.request.CategoryCreationRequestModel;
import com.ccrt.onlineshop.model.response.CategoryRest;
import com.ccrt.onlineshop.service.CategoryService;
import com.ccrt.onlineshop.shared.dto.CategoryDto;

@RestController
@RequestMapping("categories")
public class CategoryController {

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private CategoryService categoryService;

  @PostMapping
  public CategoryRest addCategory(@RequestPart(name = "image", required = false) MultipartFile image,
      @ModelAttribute CategoryCreationRequestModel categoryCreationRequestModel) {
    CategoryDto categoryDto = modelMapper.map(categoryCreationRequestModel, CategoryDto.class);
    categoryDto.setImage(image);
    CategoryDto createdCategoryDto = categoryService.createCategory(categoryDto);
    return modelMapper.map(createdCategoryDto, CategoryRest.class);
  }

  @GetMapping
  public List<CategoryRest> retrieveCategories() {
    List<CategoryDto> categoryDtos = categoryService.retrieveAllCategories();
    List<CategoryRest> categoryRests = new ArrayList<>();
    for (CategoryDto categoryDto : categoryDtos) {
      categoryRests.add(modelMapper.map(categoryDto, CategoryRest.class));
    }
    return categoryRests;
  }
}
