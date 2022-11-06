package com.ccrt.onlineshop.io.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.ccrt.onlineshop.io.entity.AddressEntity;

@Repository
public interface AddressRepository extends PagingAndSortingRepository<AddressEntity, Long> {
  Page<AddressEntity> findAllByUser_UserId(String userId, Pageable pageable);

  AddressEntity findByAddressId(String addressId);
}
