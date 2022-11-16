package com.ccrt.onlineshop.io.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ccrt.onlineshop.io.entity.CampaignEntity;

@Repository
public interface CampaignRepository extends PagingAndSortingRepository<CampaignEntity, Long> {
  @Query("select e from CampaignEntity e where e.startTime<=:date and e.endTime>=:date")
  Page<CampaignEntity> findValidCampaigns(@Param("date") Date date, Pageable pageable);

  CampaignEntity findByCampaignId(String campaignId);
}
