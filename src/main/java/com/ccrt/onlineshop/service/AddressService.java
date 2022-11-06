package com.ccrt.onlineshop.service;

import java.util.List;

import com.ccrt.onlineshop.shared.dto.AddressDto;

public interface AddressService {
  AddressDto createAddress(String userId, AddressDto addressDto);

  List<AddressDto> retrieveAllAddresses(String userId, int page, int limit);

  AddressDto retrieveAddress(String addressId);

  AddressDto updateDefaultAddresses(String userId, AddressDto addressDto);
}
