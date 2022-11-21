package com.ccrt.onlineshop.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ccrt.onlineshop.enums.Message;
import com.ccrt.onlineshop.enums.MessageCode;
import com.ccrt.onlineshop.exceptions.AddressServiceException;
import com.ccrt.onlineshop.model.request.AddressRequestModel;
import com.ccrt.onlineshop.model.request.DefaultAddressUpdateRequestModel;
import com.ccrt.onlineshop.model.response.AddressRest;
import com.ccrt.onlineshop.service.AddressService;
import com.ccrt.onlineshop.shared.dto.AddressDto;

@RestController
@RequestMapping("users/{userId}/addresses")
public class AddressController {
  @Autowired
  private AddressService addressService;

  @Autowired
  private ModelMapper modelMapper;

  @PostMapping
  public AddressRest createAddress(@PathVariable String userId, @RequestBody AddressRequestModel addressRequestModel,
      Principal principal) {
    if (!principal.getName().equals(userId)) {
      throw new AddressServiceException(MessageCode.FORBIDDEN.name(), Message.FORBIDDEN.getMessage(),
          HttpStatus.FORBIDDEN);
    }
    AddressDto addressDto = modelMapper.map(addressRequestModel, AddressDto.class);
    AddressDto createdAddressDto = addressService.createAddress(userId, addressDto);
    return modelMapper.map(createdAddressDto, AddressRest.class);
  }

  @GetMapping
  public List<AddressRest> retrieveAddresses(@PathVariable String userId, Principal principal,
      @RequestParam(name = "page", defaultValue = "0", required = false) int page,
      @RequestParam(name = "limit", defaultValue = "15", required = false) int limit) {
    if (!principal.getName().equals(userId)) {
      throw new AddressServiceException(MessageCode.FORBIDDEN.name(), Message.FORBIDDEN.getMessage(),
          HttpStatus.FORBIDDEN);
    }
    List<AddressDto> addressDtos = addressService.retrieveAllAddresses(userId, page, limit);
    List<AddressRest> addressRests = new ArrayList<>();
    for (AddressDto addressDto : addressDtos) {
      addressRests.add(modelMapper.map(addressDto, AddressRest.class));
    }
    return addressRests;

  }

  @PutMapping("/default-addresses")
  public AddressRest updateDefaultAddress(@PathVariable String userId, Principal principal,
      @RequestBody DefaultAddressUpdateRequestModel defaultAddressUpdateRequestModel) {
    if (!principal.getName().equals(userId)) {
      throw new AddressServiceException(MessageCode.FORBIDDEN.name(), Message.FORBIDDEN.getMessage(),
          HttpStatus.FORBIDDEN);
    }
    AddressDto addressDto = modelMapper.map(defaultAddressUpdateRequestModel, AddressDto.class);
    AddressDto updatedAddressDto = addressService.updateDefaultAddresses(userId, addressDto);
    return modelMapper.map(updatedAddressDto, AddressRest.class);
  }

  @GetMapping("/{addressId}")
  public AddressRest retrieveAddress(@PathVariable String userId, @PathVariable String addressId,
      Principal principal) {
    if (!principal.getName().equals(userId)) {
      throw new AddressServiceException(MessageCode.FORBIDDEN.name(), Message.FORBIDDEN.getMessage(),
          HttpStatus.FORBIDDEN);
    }
    AddressDto addressDto = addressService.retrieveAddress(addressId);
    return modelMapper.map(addressDto, AddressRest.class);

  }

  @DeleteMapping("/{addressId}")
  public ResponseEntity<String> removeAddress(@PathVariable String userId, @PathVariable String addressId,
      Principal principal) {
    if (!principal.getName().equals(userId)) {
      throw new AddressServiceException(MessageCode.FORBIDDEN.name(), Message.FORBIDDEN.getMessage(),
          HttpStatus.FORBIDDEN);
    }
    addressService.removeAddress(addressId, userId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);

  }
}
