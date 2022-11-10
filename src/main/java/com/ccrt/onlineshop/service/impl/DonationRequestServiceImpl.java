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

import com.ccrt.onlineshop.enums.DonationRequestStatus;
import com.ccrt.onlineshop.enums.Message;
import com.ccrt.onlineshop.enums.MessageCode;
import com.ccrt.onlineshop.exceptions.DonationServiceException;
import com.ccrt.onlineshop.io.entity.DonationRequestEntity;
import com.ccrt.onlineshop.io.repository.DonationRequestRepository;
import com.ccrt.onlineshop.service.DonationRequestService;
import com.ccrt.onlineshop.shared.Utils;
import com.ccrt.onlineshop.shared.dto.DonationRequestDto;

@Service
public class DonationRequestServiceImpl implements DonationRequestService {

  @Autowired
  private DonationRequestRepository donationRequestRepository;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private Utils utils;

  @Transactional
  @Override
  public DonationRequestDto createDonationRequest(DonationRequestDto donationRequestDto) {
    DonationRequestEntity donationRequestEntity = modelMapper.map(donationRequestDto, DonationRequestEntity.class);
    donationRequestEntity.setStatus(DonationRequestStatus.PENDING);
    donationRequestEntity.setRequestId(utils.generateDonationRequestId());
    DonationRequestEntity createdDonationRequestEntity = donationRequestRepository.save(donationRequestEntity);
    return modelMapper.map(createdDonationRequestEntity, DonationRequestDto.class);
  }

  @Override
  public List<DonationRequestDto> retrieveDonationRequests(int page, int limit, DonationRequestStatus status) {
    Page<DonationRequestEntity> dPage = donationRequestRepository.findAllByStatus(status,
        PageRequest.of(page, limit, Sort.by("creationTime").descending()));
    List<DonationRequestDto> donationRequestDtos = new ArrayList<>();
    for (DonationRequestEntity donationRequestEntity : dPage.getContent()) {
      donationRequestDtos.add(modelMapper.map(donationRequestEntity, DonationRequestDto.class));
    }
    return donationRequestDtos;
  }

  @Override
  public DonationRequestDto updateStatus(String requestId, DonationRequestDto donationRequestDto) {
    DonationRequestEntity donationRequestEntity = donationRequestRepository.findByRequestId(requestId);
    if (donationRequestEntity == null) {
      throw new DonationServiceException(MessageCode.DONATION_NOT_FOUND.name(), Message.DONATION_NOT_FOUND.getMessage(),
          HttpStatus.NOT_FOUND);
    }
    donationRequestEntity.setStatus(donationRequestDto.getStatus());
    DonationRequestEntity updatedDonationRequestEntity = donationRequestRepository.save(donationRequestEntity);
    return modelMapper.map(updatedDonationRequestEntity, DonationRequestDto.class);
  }

}
