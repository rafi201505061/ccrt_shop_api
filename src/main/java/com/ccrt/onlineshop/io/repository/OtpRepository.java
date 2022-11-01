package com.ccrt.onlineshop.io.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ccrt.onlineshop.io.entity.OtpEntity;

@Repository
public interface OtpRepository extends CrudRepository<OtpEntity, Long> {
  List<OtpEntity> findAllByEmail(String email);

  OtpEntity findByOtpId(String otpId);
}
