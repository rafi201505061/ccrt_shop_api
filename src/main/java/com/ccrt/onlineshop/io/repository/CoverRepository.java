package com.ccrt.onlineshop.io.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ccrt.onlineshop.io.entity.CoverEntity;

@Repository
public interface CoverRepository extends CrudRepository<CoverEntity, Long> {
  List<CoverEntity> findAllByOrderByCreationTimeDesc();

  CoverEntity findById(long id);
}
