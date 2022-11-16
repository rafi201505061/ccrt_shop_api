package com.ccrt.onlineshop.io.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.ccrt.onlineshop.io.entity.CampaignProductEntity;

@Repository
public interface CampaignProductRepository extends PagingAndSortingRepository<CampaignProductEntity, Long> {
  Page<CampaignProductEntity> findAllByCampaign_CampaignId(String campaignId, Pageable pageable);

  CampaignProductEntity findByCampaign_CampaignIdAndProduct_ProductId(String campaignId, String productId);
}
