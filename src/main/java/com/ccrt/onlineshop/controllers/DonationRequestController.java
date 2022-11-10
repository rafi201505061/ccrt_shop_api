package com.ccrt.onlineshop.controllers;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ccrt.onlineshop.enums.DonationRequestStatus;
import com.ccrt.onlineshop.model.request.DonationRequestCreationRequestModel;
import com.ccrt.onlineshop.model.request.DonationStatusUpdateRequestModel;
import com.ccrt.onlineshop.model.response.DonationRequestRest;
import com.ccrt.onlineshop.service.DonationRequestService;
import com.ccrt.onlineshop.shared.dto.DonationRequestDto;

@RestController
@RequestMapping("donation-requests")
public class DonationRequestController {
  @Autowired
  private DonationRequestService donationRequestService;
  @Autowired
  private ModelMapper modelMapper;

  @PostMapping
  public DonationRequestRest createDonationRequest(
      @RequestBody DonationRequestCreationRequestModel donationRequestCreationRequestModel) {
    DonationRequestDto donationRequestDto = modelMapper.map(donationRequestCreationRequestModel,
        DonationRequestDto.class);
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
    DonationRequestDto donationRequestDto = modelMapper.map(donationStatusUpdateRequestModel, DonationRequestDto.class);
    DonationRequestDto updatedDonationRequestDto = donationRequestService.updateStatus(requestId, donationRequestDto);
    return modelMapper.map(updatedDonationRequestDto, DonationRequestRest.class);
  }
}
