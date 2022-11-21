package com.ccrt.onlineshop.service;

import java.util.List;

import com.ccrt.onlineshop.enums.CampaignQueryStatus;
import com.ccrt.onlineshop.shared.dto.CampaignDto;
import com.ccrt.onlineshop.shared.dto.CampaignProductDto;

public interface CampaignService {
  CampaignDto createCampaign(CampaignDto campaignDto);

  List<CampaignDto> retrieveValidCampaigns(int page, int limit, CampaignQueryStatus campaignQueryStatus);

  List<CampaignProductDto> retrieveCampaignProducts(String campaignId, int page, int limit);

  CampaignProductDto addProduct(String campaignId, CampaignProductDto campaignProductDto);

  void removeProduct(String campaignId, String productId);

  void deleteCampaign(String CampaignId);

}
