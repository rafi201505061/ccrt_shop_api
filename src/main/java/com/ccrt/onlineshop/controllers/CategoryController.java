package com.ccrt.onlineshop.controllers;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ccrt.onlineshop.enums.Message;
import com.ccrt.onlineshop.enums.MessageCode;
import com.ccrt.onlineshop.exceptions.CategoryServiceException;
import com.ccrt.onlineshop.model.request.CategoryCreationRequestModel;
import com.ccrt.onlineshop.model.request.SubCategoryCreationRequestModel;
import com.ccrt.onlineshop.model.response.CategoryRest;
import com.ccrt.onlineshop.model.response.SubCategoryRest;
import com.ccrt.onlineshop.service.CategoryService;
import com.ccrt.onlineshop.shared.dto.CategoryDto;
import com.ccrt.onlineshop.shared.dto.SubCategoryDto;

@RestController
@RequestMapping("categories")
public class CategoryController {

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private CategoryService categoryService;

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public CategoryRest addCategory(@RequestPart(name = "image", required = false) MultipartFile image,
      @ModelAttribute CategoryCreationRequestModel categoryCreationRequestModel) {
    checkCategoryCreationRequestModel(categoryCreationRequestModel);
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

  @PostMapping(value = "/{categoryId}/sub-categories", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public SubCategoryRest addSubCategory(@RequestPart(name = "image", required = false) MultipartFile image,
      @ModelAttribute SubCategoryCreationRequestModel subCategoryCreationRequestModel,
      @PathVariable String categoryId) {
    checkSubCategoryCreationRequestModel(subCategoryCreationRequestModel);
    SubCategoryDto subCategoryDto = modelMapper.map(subCategoryCreationRequestModel, SubCategoryDto.class);
    subCategoryDto.setImage(image);
    SubCategoryDto createdSubCategoryDto = categoryService.addSubCategory(categoryId, subCategoryDto);
    return modelMapper.map(createdSubCategoryDto, SubCategoryRest.class);
  }

  @GetMapping(value = "/{categoryId}/sub-categories")
  public List<SubCategoryRest> retrieveSubCategories(@PathVariable String categoryId) {
    List<SubCategoryDto> subCategoryDtos = categoryService.retrieveAllSubCategories(categoryId);
    List<SubCategoryRest> subCategoryRests = new ArrayList<>();
    for (SubCategoryDto subCategoryDto : subCategoryDtos) {
      subCategoryRests.add(modelMapper.map(subCategoryDto, SubCategoryRest.class));
    }
    return subCategoryRests;
  }

  private void checkCategoryCreationRequestModel(CategoryCreationRequestModel categoryCreationRequestModel) {
    String title = categoryCreationRequestModel.getTitle();
    if (title == null) {
      throw new CategoryServiceException(MessageCode.TITLE_NOT_VALID.name(), Message.TITLE_NOT_VALID.getMessage(),
          HttpStatus.BAD_REQUEST);
    }
  }

  private void checkSubCategoryCreationRequestModel(SubCategoryCreationRequestModel subCategoryCreationRequestModel) {
    String title = subCategoryCreationRequestModel.getTitle();
    if (title == null) {
      throw new CategoryServiceException(MessageCode.TITLE_NOT_VALID.name(), Message.TITLE_NOT_VALID.getMessage(),
          HttpStatus.BAD_REQUEST);
    }
  }
}
