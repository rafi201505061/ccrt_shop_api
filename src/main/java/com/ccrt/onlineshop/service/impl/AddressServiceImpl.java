package com.ccrt.onlineshop.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ccrt.onlineshop.enums.DefaultAddressStatus;
import com.ccrt.onlineshop.enums.Message;
import com.ccrt.onlineshop.enums.MessageCode;
import com.ccrt.onlineshop.exceptions.AddressServiceException;
import com.ccrt.onlineshop.io.entity.AddressEntity;
import com.ccrt.onlineshop.io.entity.UserEntity;
import com.ccrt.onlineshop.io.repository.AddressRepository;
import com.ccrt.onlineshop.io.repository.UserRepository;
import com.ccrt.onlineshop.service.AddressService;
import com.ccrt.onlineshop.shared.Utils;
import com.ccrt.onlineshop.shared.dto.AddressDto;

@Service
public class AddressServiceImpl implements AddressService {

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private AddressRepository addressRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private Utils utils;

  @Transactional
  @Override
  public AddressDto createAddress(String userId, AddressDto addressDto) {
    AddressEntity addressEntity = modelMapper.map(addressDto, AddressEntity.class);
    addressEntity.setAddressId(utils.generateAddressId());
    UserEntity userEntity = userRepository.findByUserId(userId);
    if (userEntity == null) {
      throw new AddressServiceException(MessageCode.USER_NOT_FOUND.name(), Message.USER_NOT_FOUND.getMessage(),
          HttpStatus.NOT_FOUND);
    }
    addressEntity.setUser(userEntity);
    AddressEntity createdAddressEntity = addressRepository.save(addressEntity);
    if (addressDto.getDefaultAddressStatus() != DefaultAddressStatus.NONE) {

      if (addressDto.getDefaultAddressStatus() == DefaultAddressStatus.DEFAULT_BILLING_ADDRESS
          || addressDto.getDefaultAddressStatus() == DefaultAddressStatus.BOTH) {
        userEntity.setDefaultBillingAddress(createdAddressEntity);
        userRepository.save(userEntity);
      }
      if (addressDto.getDefaultAddressStatus() == DefaultAddressStatus.DEFAULT_SHIPPING_ADDRESS
          || addressDto.getDefaultAddressStatus() == DefaultAddressStatus.BOTH) {
        userEntity.setDefaultShippingAddress(createdAddressEntity);
        userRepository.save(userEntity);
      }
    }
    return modelMapper.map(createdAddressEntity, AddressDto.class);
  }

  @Override
  public List<AddressDto> retrieveAllAddresses(String userId, int page, int limit) {
    Page<AddressEntity> addressPage = addressRepository.findAllByUser_UserId(userId,
        PageRequest.of(page, limit, Sort.by("creationTime").descending()));
    List<AddressEntity> addressEntities = addressPage.getContent();
    List<AddressDto> addressDtos = new ArrayList<>();
    for (AddressEntity addressEntity : addressEntities) {
      addressDtos.add(modelMapper.map(addressEntity, AddressDto.class));
    }
    return addressDtos;
  }

  @Override
  public AddressDto retrieveAddress(String addressId) {
    AddressEntity addressEntity = addressRepository.findByAddressId(addressId);
    if (addressEntity == null) {
      throw new AddressServiceException(MessageCode.ADDRESS_NOT_FOUND.name(), Message.ADDRESS_NOT_FOUND.getMessage(),
          HttpStatus.NOT_FOUND);
    }
    return modelMapper.map(addressEntity, AddressDto.class);

  }

  @Transactional
  @Override
  public AddressDto updateDefaultAddresses(String userId, AddressDto addressDto) {
    AddressEntity addressEntity = addressRepository.findByAddressId(addressDto.getAddressId());
    if (addressEntity == null) {
      throw new AddressServiceException(MessageCode.ADDRESS_NOT_FOUND.name(), Message.ADDRESS_NOT_FOUND.getMessage(),
          HttpStatus.NOT_FOUND);
    }
    UserEntity userEntity = userRepository.findByUserId(userId);
    if (userEntity == null) {
      throw new AddressServiceException(MessageCode.USER_NOT_FOUND.name(), Message.USER_NOT_FOUND.getMessage(),
          HttpStatus.NOT_FOUND);
    }
    if (addressEntity.getUser().getUserId().equals(userId)) {
      if (addressDto.getDefaultAddressStatus() != DefaultAddressStatus.NONE) {

        if (addressDto.getDefaultAddressStatus() == DefaultAddressStatus.DEFAULT_BILLING_ADDRESS) {
          userEntity.setDefaultBillingAddress(addressEntity);
          userRepository.save(userEntity);
        }
        if (addressDto.getDefaultAddressStatus() == DefaultAddressStatus.DEFAULT_SHIPPING_ADDRESS) {
          userEntity.setDefaultShippingAddress(addressEntity);
          userRepository.save(userEntity);
        }
      }
    }
    return modelMapper.map(addressEntity, AddressDto.class);
  }

}
