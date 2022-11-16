package com.ccrt.onlineshop.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ccrt.onlineshop.enums.Message;
import com.ccrt.onlineshop.enums.MessageCode;
import com.ccrt.onlineshop.enums.Role;
import com.ccrt.onlineshop.exceptions.UserServiceException;
import com.ccrt.onlineshop.io.entity.UserEntity;
import com.ccrt.onlineshop.io.repository.UserRepository;
import com.ccrt.onlineshop.service.UserService;
import com.ccrt.onlineshop.shared.AmazonSES;
import com.ccrt.onlineshop.shared.Utils;
import com.ccrt.onlineshop.shared.dto.UserDto;

@Service
public class UserServiceImpl implements UserService {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private Utils utils;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  private AmazonSES amazonSES;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    UserEntity userEntity = userRepository.findByEmail(email);
    if (userEntity == null)
      throw new UsernameNotFoundException(email);
    List<SimpleGrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority(userEntity.getRole().name()));
    return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), authorities);
  }

  @Transactional
  @Override
  public UserDto createUser(UserDto userDto, Role role) {
    try {
      UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
      userEntity.setUserId(utils.generateUserId());
      userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
      userEntity.setRole(role);
      UserEntity createdUserEntity = userRepository.save(userEntity);
      return modelMapper.map(createdUserEntity, UserDto.class);
    } catch (DataIntegrityViolationException e) {
      e.printStackTrace();
      throw new UserServiceException("ACCOUNT_ALREADY_EXISTS",
          "You already have an account with this email address. Please sign in.", HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

  }

  @Override
  public UserDto getUserByEmail(String email) {
    UserEntity foundUserEntity = userRepository.findByEmail(email);
    if (foundUserEntity == null)
      throw new UserServiceException(MessageCode.USER_NOT_FOUND.name(), Message.USER_NOT_FOUND.getMessage(),
          HttpStatus.NOT_FOUND);
    return modelMapper.map(foundUserEntity, UserDto.class);
  }

  @Override
  public UserDto getUserByUserId(String userId) {
    UserEntity foundUserEntity = userRepository.findByUserId(userId);
    if (foundUserEntity == null)
      throw new UserServiceException(MessageCode.USER_NOT_FOUND.name(), Message.USER_NOT_FOUND.getMessage(),
          HttpStatus.NOT_FOUND);
    return modelMapper.map(foundUserEntity, UserDto.class);
  }

  @Transactional
  @Override
  public UserDto updateUser(String userId, UserDto userDto) {
    UserEntity foundUserEntity = userRepository.findByUserId(userId);
    if (foundUserEntity == null)
      throw new UserServiceException(MessageCode.USER_NOT_FOUND.name(), Message.USER_NOT_FOUND.getMessage(),
          HttpStatus.NOT_FOUND);
    if (userDto.getFirstName() != null)
      foundUserEntity.setFirstName(userDto.getFirstName());
    if (userDto.getLastName() != null)
      foundUserEntity.setLastName(userDto.getLastName());
    UserEntity updatedUserEntity = userRepository.save(foundUserEntity);
    return modelMapper.map(updatedUserEntity, UserDto.class);
  }

  @Transactional
  @Override
  public void updatePassword(String userId, UserDto userDto) {
    UserEntity foundUserEntity = userRepository.findByUserId(userId);
    if (foundUserEntity == null)
      throw new UserServiceException(MessageCode.USER_NOT_FOUND.name(), Message.USER_NOT_FOUND.getMessage(),
          HttpStatus.NOT_FOUND);
    if (bCryptPasswordEncoder.matches(userDto.getPrevPassword(), foundUserEntity.getEncryptedPassword())) {
      foundUserEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
      userRepository.save(foundUserEntity);
    } else {
      throw new UserServiceException(MessageCode.WRONG_PASSWORD.name(), Message.WRONG_PASSWORD.getMessage(),
          HttpStatus.EXPECTATION_FAILED);
    }

  }

  @Transactional
  @Override
  public void sendPasswordResetCode(String userId) {
    UserEntity foundUserEntity = userRepository.findByUserId(userId);
    if (foundUserEntity == null)
      throw new UserServiceException(MessageCode.USER_NOT_FOUND.name(), Message.USER_NOT_FOUND.getMessage(),
          HttpStatus.NOT_FOUND);
    String passwordResetCode = utils.generatePasswordResetCode();
    foundUserEntity.setPasswordResetToken(passwordResetCode);
    userRepository.save(foundUserEntity);
    amazonSES.sendPasswordResetCode(modelMapper.map(foundUserEntity, UserDto.class), passwordResetCode);
  }

  @Transactional
  @Override
  public void resetPassword(String userId, UserDto userDto) {
    UserEntity foundUserEntity = userRepository.findByUserId(userId);
    if (foundUserEntity == null)
      throw new UserServiceException(MessageCode.USER_NOT_FOUND.name(), Message.USER_NOT_FOUND.getMessage(),
          HttpStatus.NOT_FOUND);
    if (foundUserEntity.getPasswordResetToken() == null
        || !foundUserEntity.getPasswordResetToken().equals(userDto.getPasswordResetToken())) {
      throw new UserServiceException(MessageCode.PASSWORD_RESET_CODE_MISMATCH.name(),
          Message.PASSWORD_RESET_CODE_MISMATCH.getMessage(),
          HttpStatus.FORBIDDEN);
    }
    foundUserEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
    foundUserEntity.setPasswordResetToken(null);
    userRepository.save(foundUserEntity);
  }

}
