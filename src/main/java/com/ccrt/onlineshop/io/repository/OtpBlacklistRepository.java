package com.ccrt.onlineshop.io.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ccrt.onlineshop.io.entity.OtpBlacklistEntity;

@Repository
public interface OtpBlacklistRepository extends CrudRepository<OtpBlacklistEntity, Long> {
  List<OtpBlacklistEntity> findAllByEmail(String email);
}
