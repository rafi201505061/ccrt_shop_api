package com.ccrt.onlineshop.controllers;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ccrt.onlineshop.enums.DonationRequestStatus;
import com.ccrt.onlineshop.enums.MessageCode;
import com.ccrt.onlineshop.exceptions.DonationServiceException;
import com.ccrt.onlineshop.model.request.DonationRequestCreationRequestModel;
import com.ccrt.onlineshop.model.request.DonationStatusUpdateRequestModel;
import com.ccrt.onlineshop.model.response.DonationRequestRest;
import com.ccrt.onlineshop.service.DonationRequestService;
import com.ccrt.onlineshop.shared.Utils;
import com.ccrt.onlineshop.shared.dto.DonationRequestDto;

@RestController
@RequestMapping("donation-requests")
public class DonationRequestController {
  @Autowired
  private DonationRequestService donationRequestService;
  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private Utils utils;

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public DonationRequestRest createDonationRequest(@RequestPart(value = "image", required = false) MultipartFile image,
      @ModelAttribute DonationRequestCreationRequestModel donationRequestCreationRequestModel) {
    checkDonationRequestCreationRequestModel(donationRequestCreationRequestModel);
    DonationRequestDto donationRequestDto = modelMapper.map(donationRequestCreationRequestModel,
        DonationRequestDto.class);
    donationRequestDto.setImage(image);
    DonationRequestDto createdDonationRequestDto = donationRequestService.createDonationRequest(donationRequestDto);
    return modelMapper.map(createdDonationRequestDto, DonationRequestRest.class);
  }

  @GetMapping
  public List<DonationRequestRest> retrieveDonationRequests(
      @RequestParam(name = "page", required = false, defaultValue = "0") int page,
      @RequestParam(name = "limit", required = false, defaultValue = "15") int limit,
      @RequestParam(name = "status", required = false, defaultValue = "PENDING") DonationRequestStatus status) {
    List<DonationRequestDto> donationRequestDtos = donationRequestService.retrieveDonationRequests(page, limit, status);
    List<DonationRequestRest> donationRequestRests = new ArrayList<>();
    for (DonationRequestDto donationRequestDto : donationRequestDtos) {
      donationRequestRests.add(modelMapper.map(donationRequestDto, DonationRequestRest.class));
    }
    return donationRequestRests;
  }

  @PutMapping("/{requestId}")
  public DonationRequestRest updateStatus(@PathVariable String requestId,
      @RequestBody DonationStatusUpdateRequestModel donationStatusUpdateRequestModel) {
    checkDonationStatusUpdateRequestModel(donationStatusUpdateRequestModel);
    DonationRequestDto donationRequestDto = modelMapper.map(donationStatusUpdateRequestModel, DonationRequestDto.class);
    DonationRequestDto updatedDonationRequestDto = donationRequestService.updateStatus(requestId, donationRequestDto);
    return modelMapper.map(updatedDonationRequestDto, DonationRequestRest.class);
  }

  private void checkDonationStatusUpdateRequestModel(
      DonationStatusUpdateRequestModel donationStatusUpdateRequestModel) {
    DonationRequestStatus status = donationStatusUpdateRequestModel.getStatus();
    if (status == null) {
      throw new DonationServiceException(MessageCode.STATUS_NOT_VALID.name(),
          MessageCode.STATUS_NOT_VALID.name(),
          HttpStatus.BAD_REQUEST);
    }
  }

  private void checkDonationRequestCreationRequestModel(
      DonationRequestCreationRequestModel donationRequestCreationRequestModel) {
    String donorName = donationRequestCreationRequestModel.getDonorName();
    String address = donationRequestCreationRequestModel.getAddress();
    String productTitle = donationRequestCreationRequestModel.getProductTitle();
    int numItems = donationRequestCreationRequestModel.getNumItems();
    String phoneNo = donationRequestCreationRequestModel.getPhoneNo();

    if (!utils.isNonNullAndNonEmpty(donorName)) {
      throw new DonationServiceException(MessageCode.DONOR_NAME_NOT_VALID.name(),
          MessageCode.DONOR_NAME_NOT_VALID.name(),
          HttpStatus.BAD_REQUEST);
    }

    if (!utils.isNonNullAndNonEmpty(phoneNo)) {
      throw new DonationServiceException(MessageCode.PHONE_NO_NOT_VALID.name(),
          MessageCode.PHONE_NO_NOT_VALID.name(),
          HttpStatus.BAD_REQUEST);
    }
    if (!utils.isNonNullAndNonEmpty(address)) {
      throw new DonationServiceException(MessageCode.ADDRESS_NOT_VALID.name(),
          MessageCode.ADDRESS_NOT_VALID.name(),
          HttpStatus.BAD_REQUEST);
    }
    if (!utils.isNonNullAndNonEmpty(productTitle)) {
      throw new DonationServiceException(MessageCode.PRODUCT_TITLE_NOT_VALID.name(),
          MessageCode.PRODUCT_TITLE_NOT_VALID.name(),
          HttpStatus.BAD_REQUEST);
    }
    if (numItems <= 0) {
      throw new DonationServiceException(MessageCode.NUM_ITEMS_NOT_VALID.name(),
          MessageCode.NUM_ITEMS_NOT_VALID.name(),
          HttpStatus.BAD_REQUEST);
    }

  }
}
