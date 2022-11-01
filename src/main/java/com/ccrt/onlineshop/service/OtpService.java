package com.ccrt.onlineshop.service;

import com.ccrt.onlineshop.shared.dto.OtpDto;

public interface OtpService {
  OtpDto sendOtp(OtpDto otpDto);

  void validateOtp(OtpDto otpDto);
}
