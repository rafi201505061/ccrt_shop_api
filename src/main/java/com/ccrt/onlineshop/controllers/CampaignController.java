package com.ccrt.onlineshop.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ccrt.onlineshop.enums.CampaignQueryStatus;
import com.ccrt.onlineshop.enums.Message;
import com.ccrt.onlineshop.enums.MessageCode;
import com.ccrt.onlineshop.exceptions.CampaignServiceException;
import com.ccrt.onlineshop.model.request.CampaignCreationRequestModel;
import com.ccrt.onlineshop.model.request.CampaignProductCreationRequestModel;
import com.ccrt.onlineshop.model.response.CampaignProductRest;
import com.ccrt.onlineshop.model.response.CampaignRest;
import com.ccrt.onlineshop.service.CampaignService;
import com.ccrt.onlineshop.shared.Utils;
import com.ccrt.onlineshop.shared.dto.CampaignDto;
import com.ccrt.onlineshop.shared.dto.CampaignProductDto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("campaigns")
public class CampaignController {
  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private CampaignService campaignService;

  @Autowired
  private Utils utils;

  @PostMapping()
  public CampaignRest createCampaign(@RequestBody CampaignCreationRequestModel campaignCreationRequestModel) {
    checkCampaignCreationRequestModel(campaignCreationRequestModel);
    CampaignDto campaignDto = modelMapper.map(campaignCreationRequestModel, CampaignDto.class);
    CampaignDto createdCampaignDto = campaignService.createCampaign(campaignDto);
    return modelMapper.map(createdCampaignDto, CampaignRest.class);
  }

  @GetMapping
  public List<CampaignRest> retrieveCampaigns(
      @RequestParam(name = "page", required = false, defaultValue = "0") int page,
      @RequestParam(name = "limit", required = false, defaultValue = "15") int limit,
      @RequestParam(name = "status", required = false, defaultValue = "RUNNING") CampaignQueryStatus campaignQueryStatus) {
    List<CampaignDto> campaignDtos = campaignService.retrieveValidCampaigns(page, limit, campaignQueryStatus);
    List<CampaignRest> campaignRests = new ArrayList<>();
    for (CampaignDto campaignDto : campaignDtos) {
      campaignRests.add(modelMapper.map(campaignDto, CampaignRest.class));
    }
    return campaignRests;
  }

  @PostMapping("/{campaignId}/products")
  public CampaignProductRest addNewProduct(@PathVariable String campaignId,
      @RequestBody CampaignProductCreationRequestModel campaignProductCreationRequestModel) {
    CampaignProductDto campaignProductDto = modelMapper.map(campaignProductCreationRequestModel,
        CampaignProductDto.class);
    CampaignProductDto createdCampaignProductDto = campaignService.addProduct(campaignId, campaignProductDto);
    return modelMapper.map(createdCampaignProductDto, CampaignProductRest.class);
  }

  @DeleteMapping("/{campaignId}/products/{productId}")
  public ResponseEntity<String> removeProduct(@PathVariable String campaignId,
      @PathVariable String productId) {

    campaignService.removeProduct(campaignId, productId);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping("/{campaignId}/products")
  public List<CampaignProductRest> retrieveCampaignProducts(
      @PathVariable String campaignId,
      @RequestParam(name = "page", required = false, defaultValue = "0") int page,
      @RequestParam(name = "limit", required = false, defaultValue = "15") int limit) {
    List<CampaignProductDto> campaignProductDtos = campaignService.retrieveCampaignProducts(campaignId, page, limit);
    List<CampaignProductRest> campaignProductRests = new ArrayList<>();
    for (CampaignProductDto campaignProductDto : campaignProductDtos) {
      campaignProductRests.add(modelMapper.map(campaignProductDto, CampaignProductRest.class));
    }
    return campaignProductRests;
  }

  @DeleteMapping("/{campaignId}")
  public ResponseEntity<String> deleteCampaign(
      @PathVariable String campaignId) {
    campaignService.deleteCampaign(campaignId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  private void checkCampaignCreationRequestModel(CampaignCreationRequestModel campaignCreationRequestModel) {
    String title = campaignCreationRequestModel.getTitle();
    String description = campaignCreationRequestModel.getDescription();
    Date startDate = campaignCreationRequestModel.getStartTime();
    long durationInDays = campaignCreationRequestModel.getDurationInDays();
    double priceReductionPercentage = campaignCreationRequestModel.getPriceReductionPercentage();
    if (!utils.isNonNullAndNonEmpty(title)) {
      throw new CampaignServiceException(MessageCode.TITLE_NOT_VALID.name(), Message.TITLE_NOT_VALID.getMessage(),
          HttpStatus.BAD_REQUEST);
    }
    if (!utils.isNonNullAndNonEmpty(description)) {
      throw new CampaignServiceException(MessageCode.DESCRIPTION_NOT_VALID.name(),
          Message.DESCRIPTION_NOT_VALID.getMessage(),
          HttpStatus.BAD_REQUEST);
    }
    if (startDate == null) {
      throw new CampaignServiceException(MessageCode.DATE_NOT_VALID.name(), Message.DATE_NOT_VALID.getMessage(),
          HttpStatus.BAD_REQUEST);
    }
    if (durationInDays <= 0) {
      throw new CampaignServiceException(MessageCode.DURATION_NOT_VALID.name(), Message.DURATION_NOT_VALID.getMessage(),
          HttpStatus.BAD_REQUEST);
    }
    if (priceReductionPercentage <= 0 || priceReductionPercentage > 100) {
      throw new CampaignServiceException(MessageCode.PRICE_REDUCTION_PERCENTAGE_NOT_VALID.name(),
          Message.PRICE_REDUCTION_PERCENTAGE_NOT_VALID.getMessage(),
          HttpStatus.BAD_REQUEST);
    }
  }

}
