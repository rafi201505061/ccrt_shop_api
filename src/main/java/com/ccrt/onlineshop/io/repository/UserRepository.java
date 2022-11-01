package com.ccrt.onlineshop.io.repository;

import org.springframework.data.repository.CrudRepository;

import com.ccrt.onlineshop.io.entity.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
  UserEntity findByEmail(String email);

  UserEntity findByUserId(String userId);
}
