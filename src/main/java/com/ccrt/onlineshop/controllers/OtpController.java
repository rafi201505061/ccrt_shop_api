package com.ccrt.onlineshop.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ccrt.onlineshop.model.request.OtpCreationRequestModel;
import com.ccrt.onlineshop.model.request.OtpValidationRequestModel;
import com.ccrt.onlineshop.model.response.OtpRest;
import com.ccrt.onlineshop.service.OtpService;
import com.ccrt.onlineshop.shared.dto.OtpDto;

@RestController
@RequestMapping("otp")
public class OtpController {

  @Autowired
  private OtpService otpService;

  @Autowired
  private ModelMapper modelMapper;

  @PostMapping
  public OtpRest sendOtp(
      @RequestBody OtpCreationRequestModel otpCreationRequestModel) {
    OtpDto otpDto = modelMapper.map(otpCreationRequestModel, OtpDto.class);
    OtpDto createdOtp = otpService.sendOtp(otpDto);
    OtpRest otpRest = modelMapper.map(createdOtp, OtpRest.class);
    return otpRest;
  }

  @PostMapping("/validation")
  public OtpRest validateOtp(
      @RequestBody OtpValidationRequestModel otpValidationRequestModel) {
    OtpDto otpDto = modelMapper.map(otpValidationRequestModel, OtpDto.class);
    otpService.validateOtp(otpDto);
    return null;
  }
}
