package com.ccrt.onlineshop.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ccrt.onlineshop.enums.Message;
import com.ccrt.onlineshop.enums.MessageCode;
import com.ccrt.onlineshop.exceptions.CategoryServiceException;
import com.ccrt.onlineshop.io.entity.CategoryEntity;
import com.ccrt.onlineshop.io.entity.CategoryList;
import com.ccrt.onlineshop.io.entity.PromotedCategoryEntity;
import com.ccrt.onlineshop.io.entity.SubCategoryEntity;
import com.ccrt.onlineshop.io.repository.CategoryRepository;
import com.ccrt.onlineshop.io.repository.PromotedCategoryRepository;
import com.ccrt.onlineshop.io.repository.SubCategoryRepository;
import com.ccrt.onlineshop.service.CategoryService;
import com.ccrt.onlineshop.shared.FileUploadUtil;
import com.ccrt.onlineshop.shared.Utils;
import com.ccrt.onlineshop.shared.dto.CategoryDto;
import com.ccrt.onlineshop.shared.dto.PromotedCategoryDto;
import com.ccrt.onlineshop.shared.dto.SubCategoryDto;

@Service
public class CategoryServiceImpl implements CategoryService {

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private SubCategoryRepository subCategoryRepository;

  @Autowired
  private PromotedCategoryRepository promotedCategoryRepository;

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
      String fileName = categoryId + "." + utils.getFileExtension(categoryDto.getImage().getOriginalFilename());
      fileUploadUtil.saveFile(FileUploadUtil.CATEGORY_UPLOAD_DIR, fileName, categoryDto.getImage());
      categoryEntity.setImageUrl("/categories/" + fileName);
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

  @Transactional
  @Override
  public SubCategoryDto addSubCategory(String categoryId, SubCategoryDto subCategoryDto) {
    try {
      CategoryEntity categoryEntity = categoryRepository.findByCategoryId(categoryId);
      if (categoryEntity == null) {
        throw new CategoryServiceException(MessageCode.CATEGORY_NOT_FOUND.name(),
            Message.CATEGORY_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND);
      }
      SubCategoryEntity subCategoryEntity = modelMapper.map(subCategoryDto, SubCategoryEntity.class);
      String subCategoryId = utils.generateCategoryId();
      subCategoryEntity.setSubCategoryId(subCategoryId);
      subCategoryEntity.setCategory(categoryEntity);
      String fileName = subCategoryId + "." + utils.getFileExtension(subCategoryDto.getImage().getOriginalFilename());
      fileUploadUtil.saveFile(FileUploadUtil.SUB_CATEGORY_UPLOAD_DIR, fileName, subCategoryDto.getImage());
      subCategoryEntity.setImageUrl("/sub-categories/" + fileName);
      SubCategoryEntity createdSubCategoryEntity = subCategoryRepository.save(subCategoryEntity);
      return modelMapper.map(createdSubCategoryEntity, SubCategoryDto.class);
    } catch (IOException e) {
      throw new CategoryServiceException(MessageCode.FILE_CREATION_ERROR.name(),
          Message.FILE_CREATION_ERROR.getMessage());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public List<SubCategoryDto> retrieveAllSubCategories(String categoryId) {
    List<SubCategoryEntity> subCategoryEntities = subCategoryRepository
        .findAllByCategory_CategoryIdOrderByTitleDesc(categoryId);
    List<SubCategoryDto> subCategoryDtos = new ArrayList<>();
    for (SubCategoryEntity subCategoryEntity : subCategoryEntities) {
      subCategoryDtos.add(modelMapper.map(subCategoryEntity, SubCategoryDto.class));
    }
    return subCategoryDtos;
  }

  @Transactional
  @Override
  public CategoryDto updateCategory(String categoryId, CategoryDto categoryDto) {
    try {
      CategoryEntity categoryEntity = categoryRepository.findByCategoryId(categoryId);
      if (categoryEntity == null) {
        throw new CategoryServiceException(MessageCode.CATEGORY_NOT_FOUND.name(),
            Message.CATEGORY_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND);
      }
      if (categoryDto.getImage() != null) {
        String fileName = categoryId + "." + utils.getFileExtension(categoryDto.getImage().getOriginalFilename());
        fileUploadUtil.saveFile(FileUploadUtil.CATEGORY_UPLOAD_DIR, fileName, categoryDto.getImage());
        categoryEntity.setImageUrl(FileUploadUtil.CATEGORY_UPLOAD_DIR + "\\" + fileName);
      }
      if (categoryDto.getTitle() != null) {
        categoryEntity.setTitle(categoryDto.getTitle());
      }
      CategoryEntity updatedCategoryEntity = categoryRepository.save(categoryEntity);
      return modelMapper.map(updatedCategoryEntity, CategoryDto.class);
    } catch (IOException e) {
      throw new CategoryServiceException(MessageCode.FILE_CREATION_ERROR.name(),
          Message.FILE_CREATION_ERROR.getMessage());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Transactional
  @Override
  public SubCategoryDto updateSubCategory(String categoryId, String subCategoryId, SubCategoryDto subCategoryDto) {
    try {
      SubCategoryEntity subCategoryEntity = subCategoryRepository.findBySubCategoryId(subCategoryId);
      if (subCategoryEntity == null) {
        throw new CategoryServiceException(MessageCode.SUB_CATEGORY_NOT_FOUND.name(),
            Message.SUB_CATEGORY_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND);
      }
      if (subCategoryDto.getImage() != null) {
        String fileName = categoryId + "." + utils.getFileExtension(subCategoryDto.getImage().getOriginalFilename());
        fileUploadUtil.saveFile(FileUploadUtil.CATEGORY_UPLOAD_DIR, fileName, subCategoryDto.getImage());
        subCategoryEntity.setImageUrl(FileUploadUtil.CATEGORY_UPLOAD_DIR + "\\" + fileName);
      }
      if (subCategoryDto.getTitle() != null) {
        subCategoryEntity.setTitle(subCategoryDto.getTitle());
      }
      SubCategoryEntity updatedSubCategoryEntity = subCategoryRepository.save(subCategoryEntity);
      return modelMapper.map(updatedSubCategoryEntity, SubCategoryDto.class);
    } catch (IOException e) {
      throw new CategoryServiceException(MessageCode.FILE_CREATION_ERROR.name(),
          Message.FILE_CREATION_ERROR.getMessage());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public PromotedCategoryDto changePromotedCategory(PromotedCategoryDto promotedCategoryDto) {
    SubCategoryEntity subCategoryEntity = subCategoryRepository
        .findBySubCategoryId(promotedCategoryDto.getSubCategoryId());
    if (subCategoryEntity == null) {
      throw new CategoryServiceException(MessageCode.SUB_CATEGORY_NOT_FOUND.name(),
          Message.SUB_CATEGORY_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND);
    }
    PromotedCategoryEntity promotedCategoryEntity = promotedCategoryRepository
        .findBySlotNo(promotedCategoryDto.getSlotNo());
    if (promotedCategoryEntity == null) {
      promotedCategoryEntity = modelMapper.map(promotedCategoryDto,
          PromotedCategoryEntity.class);
      promotedCategoryEntity.setSubCategory(subCategoryEntity);
    } else {
      promotedCategoryEntity.setSubCategory(subCategoryEntity);
    }
    PromotedCategoryEntity updatedPromotedCategory = promotedCategoryRepository.save(promotedCategoryEntity);
    return modelMapper.map(updatedPromotedCategory, PromotedCategoryDto.class);
  }

  @Override
  public List<PromotedCategoryDto> retrievePromotedCategories() {
    List<PromotedCategoryEntity> promotedCategoryEntities = promotedCategoryRepository.findAll();
    List<PromotedCategoryDto> promotedCategoryDtos = new ArrayList<>();
    for (PromotedCategoryEntity promotedCategoryEntity : promotedCategoryEntities) {
      promotedCategoryDtos.add(modelMapper.map(promotedCategoryEntity, PromotedCategoryDto.class));
    }
    return promotedCategoryDtos;
  }

  @Override
  public SubCategoryDto retrieveSubCategoryBySubCategoryId(String subCategoryId) {
    SubCategoryEntity subCategoryEntity = subCategoryRepository.findBySubCategoryId(subCategoryId);
    if (subCategoryEntity == null) {
      throw new CategoryServiceException(MessageCode.SUB_CATEGORY_NOT_FOUND.name(),
          Message.SUB_CATEGORY_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND);
    }
    return modelMapper.map(subCategoryEntity, SubCategoryDto.class);
  }

  @Override
  public List<CategoryDto> retrieveCategoryList() {
    List<CategoryList> categoryList = subCategoryRepository.findCategoryListWithSubcategoryList();
    List<CategoryDto> categoryDtos = new ArrayList<>();
    for (CategoryList category : categoryList) {
      String categoryId = category.getCategoryId();
      String categoryTitle = category.getCategoryTitle();
      String categoryImageUrl = category.getCategoryImageUrl();
      String subCategoryIds = category.getSubCategoryIds();
      String subCategoryTitles = category.getSubCategoryTitles();
      String subCategoryImageUrls = category.getSubCategoryImageUrls();
      CategoryDto categoryDto = new CategoryDto();
      categoryDto.setCategoryId(categoryId);
      categoryDto.setTitle(categoryTitle);
      categoryDto.setImageUrl(categoryImageUrl);
      StringTokenizer ids = new StringTokenizer(subCategoryIds, ",");
      StringTokenizer titles = new StringTokenizer(subCategoryTitles, ",");
      StringTokenizer imageUrls = new StringTokenizer(subCategoryImageUrls, ",");
      List<SubCategoryDto> subCategories = new ArrayList<>();
      while (ids.hasMoreTokens()) {
        SubCategoryDto subCategoryDto = new SubCategoryDto();
        subCategoryDto.setSubCategoryId(ids.nextToken());
        subCategoryDto.setTitle(titles.nextToken());
        subCategoryDto.setImageUrl(imageUrls.nextToken());
        subCategories.add(subCategoryDto);

      }
      categoryDto.setSubCategories(subCategories);
      categoryDtos.add(categoryDto);
    }
    return categoryDtos;
  }

}
