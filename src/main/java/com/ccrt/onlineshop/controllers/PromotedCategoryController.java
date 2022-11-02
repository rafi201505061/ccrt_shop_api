package com.ccrt.onlineshop.controllers;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ccrt.onlineshop.model.request.PromotedCategoryUpdateRequestModel;
import com.ccrt.onlineshop.model.response.PromotedCategoryRest;
import com.ccrt.onlineshop.service.CategoryService;
import com.ccrt.onlineshop.shared.dto.PromotedCategoryDto;

@RestController
@RequestMapping("promoted-categories")
public class PromotedCategoryController {

  @Autowired
  private CategoryService categoryService;

  @Autowired
  private ModelMapper modelMapper;

  @PostMapping
  public PromotedCategoryRest changePromotedCategory(
      @RequestBody PromotedCategoryUpdateRequestModel promotedCategoryUpdateRequestModel) {
    PromotedCategoryDto promotedCategoryDto = modelMapper.map(promotedCategoryUpdateRequestModel,
        PromotedCategoryDto.class);
    PromotedCategoryDto createdPromotedCategory = categoryService.changePromotedCategory(promotedCategoryDto);
    return modelMapper.map(createdPromotedCategory, PromotedCategoryRest.class);
  }

  @GetMapping
  public List<PromotedCategoryRest> retrievePromotedCategories() {
    List<PromotedCategoryDto> promotedCategoryDtos = categoryService.retrievePromotedCategories();
    List<PromotedCategoryRest> promotedCategoryRests = new ArrayList<>();
    for (PromotedCategoryDto promotedCategoryDto : promotedCategoryDtos) {
      promotedCategoryRests.add(modelMapper.map(promotedCategoryDto, PromotedCategoryRest.class));
    }
    return promotedCategoryRests;
  }
}
