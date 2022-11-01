package com.ccrt.onlineshop.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.ccrt.onlineshop.enums.Role;
import com.ccrt.onlineshop.shared.dto.UserDto;

public interface UserService extends UserDetailsService {
  UserDto createUser(UserDto userDto, Role role);

  UserDto getUserByEmail(String email);

  UserDto getUserByUserId(String userId);

  UserDto updateUser(String userId, UserDto userDto);

  void updatePassword(String userId, UserDto userDto);

  void sendPasswordResetCode(String userId);

  void resetPassword(String userId, UserDto userDto);

}
