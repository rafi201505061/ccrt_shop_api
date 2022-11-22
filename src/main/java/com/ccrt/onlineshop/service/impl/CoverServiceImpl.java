package com.ccrt.onlineshop.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ccrt.onlineshop.enums.CoverType;
import com.ccrt.onlineshop.enums.Message;
import com.ccrt.onlineshop.enums.MessageCode;
import com.ccrt.onlineshop.exceptions.CoverServiceException;
import com.ccrt.onlineshop.io.entity.CoverEntity;
import com.ccrt.onlineshop.io.entity.ProductEntity;
import com.ccrt.onlineshop.io.entity.SubCategoryEntity;
import com.ccrt.onlineshop.io.repository.CoverRepository;
import com.ccrt.onlineshop.io.repository.ProductRepository;
import com.ccrt.onlineshop.io.repository.SubCategoryRepository;
import com.ccrt.onlineshop.service.CoverService;
import com.ccrt.onlineshop.shared.FileUploadUtil;
import com.ccrt.onlineshop.shared.Utils;
import com.ccrt.onlineshop.shared.dto.CoverDto;

@Service
public class CoverServiceImpl implements CoverService {
  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private CoverRepository coverRepository;

  @Autowired
  private SubCategoryRepository subCategoryRepository;
  @Autowired
  private ProductRepository productRepository;
  @Autowired
  private FileUploadUtil fileUploadUtil;

  @Autowired
  private Utils utils;

  @Transactional
  @Override
  public CoverDto createCover(CoverDto coverDto) {

    try {
      CoverEntity coverEntity = modelMapper.map(coverDto, CoverEntity.class);

      if (coverDto.getType() == CoverType.CATEGORY) {
        SubCategoryEntity subCategoryEntity = subCategoryRepository.findBySubCategoryId(coverDto.getItemId());
        if (subCategoryEntity == null) {
          throw new CoverServiceException(MessageCode.SUB_CATEGORY_NOT_FOUND.name(),
              Message.SUB_CATEGORY_NOT_FOUND.getMessage());
        }
        coverEntity.setImageUrl(subCategoryEntity.getImageUrl());
      } else if (coverDto.getType() == CoverType.PRODUCT) {
        ProductEntity productEntity = productRepository.findByProductId(coverDto.getItemId());
        if (productEntity == null) {
          throw new CoverServiceException(MessageCode.PRODUCT_NOT_FOUND.name(),
              Message.PRODUCT_NOT_FOUND.getMessage());
        }
        coverEntity.setImageUrl(productEntity.getImageUrl());
      }
      if (coverDto.getImage() != null) {
        String imageName = utils.generateImageId() + "."
            + utils.getFileExtension(coverDto.getImage().getOriginalFilename());
        fileUploadUtil.saveFile(FileUploadUtil.COVER_UPLOAD_DIR, imageName, coverDto.getImage());
        coverEntity.setImageUrl("/covers/" + imageName);
      }
      CoverEntity createdCoverEntity = coverRepository.save(coverEntity);
      return modelMapper.map(createdCoverEntity, CoverDto.class);
    } catch (IOException e) {
      throw new CoverServiceException(MessageCode.FILE_CREATION_ERROR.name(),
          Message.FILE_CREATION_ERROR.getMessage());
    }
  }

  @Override
  public List<CoverDto> retrieveCovers() {
    List<CoverEntity> coverEntities = coverRepository.findAllByOrderByCreationTimeDesc();
    List<CoverDto> coverDtos = new ArrayList<>();
    for (CoverEntity coverEntity : coverEntities) {
      coverDtos.add(modelMapper.map(coverEntity, CoverDto.class));
    }
    return coverDtos;
  }

  @Override
  public void removeCover(long coverId) {
    CoverEntity coverEntity = coverRepository.findById(coverId);
    if (coverEntity == null) {
      throw new CoverServiceException(MessageCode.COVER_NOT_FOUND.name(), Message.COVER_NOT_FOUND.getMessage(),
          HttpStatus.NOT_FOUND);
    }
    coverRepository.delete(coverEntity);

  }

}
