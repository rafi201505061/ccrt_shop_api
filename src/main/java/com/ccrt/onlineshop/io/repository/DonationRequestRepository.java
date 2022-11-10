package com.ccrt.onlineshop.io.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.ccrt.onlineshop.enums.DonationRequestStatus;
import com.ccrt.onlineshop.io.entity.DonationRequestEntity;

@Repository
public interface DonationRequestRepository extends PagingAndSortingRepository<DonationRequestEntity, Long> {
  Page<DonationRequestEntity> findAllByStatus(DonationRequestStatus status, Pageable pageable);

  DonationRequestEntity findByRequestId(String requestId);

}
