package com.ccrt.onlineshop.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ccrt.onlineshop.enums.CampaignStatus;
import com.ccrt.onlineshop.enums.Message;
import com.ccrt.onlineshop.enums.MessageCode;
import com.ccrt.onlineshop.exceptions.CampaignServiceException;
import com.ccrt.onlineshop.io.entity.CampaignEntity;
import com.ccrt.onlineshop.io.entity.CampaignProductEntity;
import com.ccrt.onlineshop.io.entity.ProductEntity;
import com.ccrt.onlineshop.io.repository.CampaignProductRepository;
import com.ccrt.onlineshop.io.repository.CampaignRepository;
import com.ccrt.onlineshop.io.repository.ProductRepository;
import com.ccrt.onlineshop.service.CampaignService;
import com.ccrt.onlineshop.shared.Utils;
import com.ccrt.onlineshop.shared.dto.CampaignDto;
import com.ccrt.onlineshop.shared.dto.CampaignProductDto;

@Service
public class CampaignServiceImpl implements CampaignService {

  @Autowired
  private CampaignRepository campaignRepository;

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private CampaignProductRepository campaignProductRepository;

  @Autowired
  private Utils utils;

  @Override
  public CampaignDto createCampaign(CampaignDto campaignDto) {
    CampaignEntity campaignEntity = modelMapper.map(campaignDto, CampaignEntity.class);
    campaignEntity.setStatus(CampaignStatus.RUNNING);
    campaignEntity.setCampaignId(utils.generateCampaignId());
    campaignEntity.setEndTime(utils.addDays(campaignDto.getStartTime(), (int) campaignDto.getDurationInDays()));
    CampaignEntity createdCampaignEntity = campaignRepository.save(campaignEntity);
    return modelMapper.map(createdCampaignEntity, CampaignDto.class);
  }

  @Override
  public List<CampaignDto> retrieveValidCampaigns(int page, int limit) {
    Page<CampaignEntity> campaignEntities = campaignRepository.findValidCampaigns(utils.addHours(new Date(), 6),
        PageRequest.of(page, limit, Sort.by("startTime").ascending()));

    List<CampaignDto> campaignDtos = new ArrayList<>();
    for (CampaignEntity campaignEntity : campaignEntities.getContent()) {
      campaignDtos.add(modelMapper.map(campaignEntity, CampaignDto.class));
    }
    return campaignDtos;
  }

  @Override
  public List<CampaignProductDto> retrieveCampaignProducts(String campaignId, int page, int limit) {
    Page<CampaignProductEntity> campaignProductEntities = campaignProductRepository
        .findAllByCampaign_CampaignId(campaignId, PageRequest.of(page, limit, Sort.by("id").descending()));
    List<CampaignProductDto> campaignProductDtos = new ArrayList<>();
    for (CampaignProductEntity campaignProductEntity : campaignProductEntities.getContent()) {
      campaignProductDtos.add(modelMapper.map(campaignProductEntity, CampaignProductDto.class));
    }
    return campaignProductDtos;
  }

  @Override
  public CampaignProductDto addProduct(String campaignId, CampaignProductDto campaignProductDto) {
    CampaignEntity campaignEntity = campaignRepository.findByCampaignId(campaignId);
    if (campaignEntity == null) {
      throw new CampaignServiceException(MessageCode.CAMPAIGN_NOT_FOUND.name(), Message.CAMPAIGN_NOT_FOUND.getMessage(),
          HttpStatus.NOT_FOUND);
    }
    ProductEntity productEntity = productRepository.findByProductId(campaignProductDto.getProductId());
    if (productEntity == null) {
      throw new CampaignServiceException(MessageCode.PRODUCT_NOT_FOUND.name(), Message.PRODUCT_NOT_FOUND.getMessage(),
          HttpStatus.NOT_FOUND);
    }
    CampaignProductEntity cpe = campaignProductRepository.findByCampaign_CampaignIdAndProduct_ProductId(campaignId,
        campaignProductDto.getProductId());
    if (cpe != null) {
      throw new CampaignServiceException("PRODUCT_ALREADY_EXISTS",
          "You have already added this product in this campaign.",
          HttpStatus.NOT_FOUND);
    }
    CampaignProductEntity campaignProductEntity = new CampaignProductEntity();
    campaignProductEntity.setCampaign(campaignEntity);
    campaignProductEntity.setProduct(productEntity);
    campaignProductEntity.setPriceReductionPercentage(campaignProductDto.getPriceReductionPercentage());
    CampaignProductEntity createdCampaignProductEntity = campaignProductRepository.save(campaignProductEntity);
    return modelMapper.map(createdCampaignProductEntity, CampaignProductDto.class);
  }

  @Override
  public void removeProduct(String campaignId, String productId) {
    CampaignProductEntity cpe = campaignProductRepository.findByCampaign_CampaignIdAndProduct_ProductId(campaignId,
        productId);
    if (cpe == null) {
      return;
    }
    campaignProductRepository.delete(cpe);
  }

}
