package com.ccrt.onlineshop.service;

import java.util.List;

import com.ccrt.onlineshop.enums.DonationRequestStatus;
import com.ccrt.onlineshop.shared.dto.DonationRequestDto;

public interface DonationRequestService {
  DonationRequestDto createDonationRequest(DonationRequestDto donationRequestDto);

  List<DonationRequestDto> retrieveDonationRequests(int page, int limit, DonationRequestStatus status);

  DonationRequestDto updateStatus(String requestId, DonationRequestDto donationRequestDto);
}
