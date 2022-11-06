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
import org.springframework.web.bind.annotation.PutMapping;
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
    checkCategoryCreationRequestModel(categoryCreationRequestModel, image);
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

  @PutMapping(value = "/{categoryId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public CategoryRest updateCategory(@RequestPart(name = "image", required = false) MultipartFile image,
      @ModelAttribute CategoryCreationRequestModel categoryCreationRequestModel,
      @PathVariable String categoryId) {
    checkCategoryUpdateRequestModel(categoryCreationRequestModel, image);
    CategoryDto categoryDto = modelMapper.map(categoryCreationRequestModel, CategoryDto.class);
    categoryDto.setImage(image);
    CategoryDto createdCategoryDto = categoryService.updateCategory(categoryId, categoryDto);
    return modelMapper.map(createdCategoryDto, CategoryRest.class);
  }

  @PostMapping(value = "/{categoryId}/sub-categories", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public SubCategoryRest addSubCategory(@RequestPart(name = "image", required = false) MultipartFile image,
      @ModelAttribute SubCategoryCreationRequestModel subCategoryCreationRequestModel,
      @PathVariable String categoryId) {
    checkSubCategoryCreationRequestModel(subCategoryCreationRequestModel, image);
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

  @PutMapping(value = "/{categoryId}/sub-categories/{subCategoryId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public SubCategoryRest updateSubCategory(@RequestPart(name = "image", required = false) MultipartFile image,
      @ModelAttribute SubCategoryCreationRequestModel subCategoryCreationRequestModel,
      @PathVariable String categoryId, @PathVariable String subCategoryId) {
    checkSubCategoryUpdateRequestModel(subCategoryCreationRequestModel, image);
    SubCategoryDto subCategoryDto = modelMapper.map(subCategoryCreationRequestModel, SubCategoryDto.class);
    subCategoryDto.setImage(image);
    SubCategoryDto createdSubCategoryDto = categoryService.updateSubCategory(categoryId, subCategoryId, subCategoryDto);
    return modelMapper.map(createdSubCategoryDto, SubCategoryRest.class);
  }

  private void checkCategoryCreationRequestModel(CategoryCreationRequestModel categoryCreationRequestModel,
      MultipartFile image) {
    String title = categoryCreationRequestModel.getTitle();
    if (title == null) {
      throw new CategoryServiceException(MessageCode.TITLE_NOT_VALID.name(), Message.TITLE_NOT_VALID.getMessage(),
          HttpStatus.BAD_REQUEST);
    }

    if (image.isEmpty()) {
      throw new CategoryServiceException(MessageCode.IMAGE_NOT_VALID.name(), Message.IMAGE_NOT_VALID.getMessage(),
          HttpStatus.BAD_REQUEST);
    }
  }

  private void checkCategoryUpdateRequestModel(CategoryCreationRequestModel categoryCreationRequestModel,
      MultipartFile image) {
    String title = categoryCreationRequestModel.getTitle();
    if (image.isEmpty() && title == null) {
      throw new CategoryServiceException("BAD_REQUEST",
          "You must provide either title or image or both to update title or image or both.",
          HttpStatus.BAD_REQUEST);
    }

  }

  private void checkSubCategoryUpdateRequestModel(SubCategoryCreationRequestModel subCategoryCreationRequestModel,
      MultipartFile image) {
    String title = subCategoryCreationRequestModel.getTitle();
    if (image.isEmpty() && title == null) {
      throw new CategoryServiceException("BAD_REQUEST",
          "You must provide either title or image or both to update title or image or both.",
          HttpStatus.BAD_REQUEST);
    }

  }

  private void checkSubCategoryCreationRequestModel(SubCategoryCreationRequestModel subCategoryCreationRequestModel,
      MultipartFile image) {
    String title = subCategoryCreationRequestModel.getTitle();
    if (title == null) {
      throw new CategoryServiceException(MessageCode.TITLE_NOT_VALID.name(), Message.TITLE_NOT_VALID.getMessage(),
          HttpStatus.BAD_REQUEST);
    }
    if (image.isEmpty()) {
      throw new CategoryServiceException(MessageCode.IMAGE_NOT_VALID.name(), Message.IMAGE_NOT_VALID.getMessage(),
          HttpStatus.BAD_REQUEST);
    }
  }
}
