package com.ccrt.onlineshop.io.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.ccrt.onlineshop.enums.DeliveryPlaceType;
import com.ccrt.onlineshop.io.entity.DeliveryCostEntity;

@Repository
public interface DeliveryCostRepository extends PagingAndSortingRepository<DeliveryCostEntity, Long> {
  DeliveryCostEntity findByPlace(DeliveryPlaceType place);
}
