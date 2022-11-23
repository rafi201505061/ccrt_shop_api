package com.ccrt.onlineshop.service.impl;

import java.io.IOException;
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
import com.ccrt.onlineshop.shared.FileUploadUtil;
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

  @Autowired
  private FileUploadUtil fileUploadUtil;

  @Transactional
  @Override
  public DonationRequestDto createDonationRequest(DonationRequestDto donationRequestDto) {
    try {
      DonationRequestEntity donationRequestEntity = modelMapper.map(donationRequestDto, DonationRequestEntity.class);
      donationRequestEntity.setStatus(DonationRequestStatus.PENDING);
      String requestId = utils.generateDonationRequestId();
      donationRequestEntity.setRequestId(requestId);
      if (donationRequestDto.getImage() != null) {
        String fileName = requestId + "." + utils.getFileExtension(donationRequestDto.getImage().getOriginalFilename());
        fileUploadUtil.saveFile(FileUploadUtil.REQUEST_DIR, fileName, donationRequestDto.getImage());
        donationRequestEntity.setImageUrl("/donation-requests/" + fileName);
      }
      DonationRequestEntity createdDonationRequestEntity = donationRequestRepository.save(donationRequestEntity);
      return modelMapper.map(createdDonationRequestEntity, DonationRequestDto.class);
    } catch (IOException e) {
      throw new DonationServiceException(MessageCode.FILE_CREATION_ERROR.name(),
          Message.FILE_CREATION_ERROR.getMessage());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
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
    if (donationRequestEntity.getStatus() != DonationRequestStatus.PENDING) {
      throw new DonationServiceException(MessageCode.STATUS_NOT_VALID.name(), Message.STATUS_NOT_VALID.getMessage(),
          HttpStatus.NOT_FOUND);
    }

    donationRequestEntity.setStatus(donationRequestDto.getStatus());
    DonationRequestEntity updatedDonationRequestEntity = donationRequestRepository.save(donationRequestEntity);
    return modelMapper.map(updatedDonationRequestEntity, DonationRequestDto.class);
  }

}
