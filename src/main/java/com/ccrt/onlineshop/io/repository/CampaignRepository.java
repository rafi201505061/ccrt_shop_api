package com.ccrt.onlineshop.io.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.ccrt.onlineshop.io.entity.CampaignEntity;

public interface CampaignRepository extends PagingAndSortingRepository<CampaignEntity, Long> {

}
