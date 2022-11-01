package com.ccrt.onlineshop.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccrt.onlineshop.enums.Message;
import com.ccrt.onlineshop.enums.MessageCode;
import com.ccrt.onlineshop.exceptions.CategoryServiceException;
import com.ccrt.onlineshop.io.entity.CategoryEntity;
import com.ccrt.onlineshop.io.repository.CategoryRepository;
import com.ccrt.onlineshop.service.CategoryService;
import com.ccrt.onlineshop.shared.FileUploadUtil;
import com.ccrt.onlineshop.shared.Utils;
import com.ccrt.onlineshop.shared.dto.CategoryDto;

@Service
public class CategoryServiceImpl implements CategoryService {

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private Utils utils;

  @Autowired
  private FileUploadUtil fileUploadUtil;

  @Transactional
  @Override
  public CategoryDto createCategory(CategoryDto categoryDto) {
    try {
      CategoryEntity categoryEntity = modelMapper.map(categoryDto, CategoryEntity.class);
      String categoryId = utils.generateCategoryId();
      categoryEntity.setCategoryId(categoryId);
      String fileName = categoryId + "." + utils.getFileExtension(categoryId);
      fileUploadUtil.saveFile(FileUploadUtil.CATEGORY_UPLOAD_DIR, fileName, categoryDto.getImage());
      categoryEntity.setImageUrl(FileUploadUtil.CATEGORY_UPLOAD_DIR + "\\" + fileName);
      CategoryEntity createdCategoryEntity = categoryRepository.save(categoryEntity);
      return modelMapper.map(createdCategoryEntity, CategoryDto.class);
    } catch (IOException e) {
      throw new CategoryServiceException(MessageCode.FILE_CREATION_ERROR.name(),
          Message.FILE_CREATION_ERROR.getMessage());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

  }

  @Override
  public List<CategoryDto> retrieveAllCategories() {
    List<CategoryEntity> categoryEntities = categoryRepository.findAllByOrderByTitleDesc();
    List<CategoryDto> categoryDtos = new ArrayList<>();
    for (CategoryEntity categoryEntity : categoryEntities) {
      categoryDtos.add(modelMapper.map(categoryEntity, CategoryDto.class));
    }
    return categoryDtos;
  }

}
