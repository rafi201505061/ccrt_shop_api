package com.ccrt.onlineshop.service.impl;

import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ccrt.onlineshop.AppProperties;
import com.ccrt.onlineshop.enums.Message;
import com.ccrt.onlineshop.enums.MessageCode;
import com.ccrt.onlineshop.exceptions.OtpServiceException;
import com.ccrt.onlineshop.io.entity.OtpBlacklistEntity;
import com.ccrt.onlineshop.io.entity.OtpEntity;
import com.ccrt.onlineshop.io.repository.OtpBlacklistRepository;
import com.ccrt.onlineshop.io.repository.OtpRepository;
import com.ccrt.onlineshop.service.OtpService;
import com.ccrt.onlineshop.shared.AmazonSES;
import com.ccrt.onlineshop.shared.Utils;
import com.ccrt.onlineshop.shared.dto.OtpDto;

@Service
public class OtpServiceImpl implements OtpService {

  @Autowired
  private OtpRepository otpRepository;

  @Autowired
  private OtpBlacklistRepository otpBlacklistRepository;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private Utils utils;

  @Autowired
  private AppProperties appProperties;

  @Autowired
  private AmazonSES amazonSES;

  @Transactional
  @Override
  public OtpDto sendOtp(OtpDto otpDto) {
    List<OtpBlacklistEntity> foundEntries = otpBlacklistRepository.findAllByEmail(otpDto.getEmail());
    boolean isBlocked = false;
    for (OtpBlacklistEntity otpBlacklistEntity : foundEntries) {
      if (utils.findDifferenceBetweenDatesInMinute(otpBlacklistEntity.getTimeOfBlocking(), new Date()) <= Long
          .parseLong(appProperties.getProperty("blockDuration"))) {
        isBlocked = true;
      }
    }
    if (isBlocked == false) {
      List<OtpEntity> allOtps = otpRepository.findAllByEmail(otpDto.getEmail());
      long otpTries = 0;
      for (OtpEntity otpEntity : allOtps) {
        if (utils.findDifferenceBetweenDatesInMinute(otpEntity.getCreationTime(), new Date()) <= Long
            .parseLong(appProperties.getProperty("blockDuration"))) {
          otpTries++;
        }
      }
      long maxConsecutiveOtpCodes = Long.parseLong(appProperties.getProperty("max-consecutive-otp-tries"));
      if ((otpTries + 1) == maxConsecutiveOtpCodes) {
        OtpBlacklistEntity otpBlacklistEntity2 = modelMapper.map(otpDto, OtpBlacklistEntity.class);
        otpBlacklistEntity2.setOtpBlacklistId(utils.generateOtpId(15));
        otpBlacklistRepository.save(otpBlacklistEntity2);
      }
      String otpCode = utils.generateOtpCode(6);
      amazonSES.sendVerificationEmail(otpDto.getEmail(), otpCode);
      OtpEntity otpEntity = modelMapper.map(otpDto, OtpEntity.class);
      otpEntity.setCode(otpCode);
      otpEntity.setOtpId(utils.generateOtpId(15));
      OtpEntity createdOtpEntity = otpRepository.save(otpEntity);
      OtpDto returnOtpDto = modelMapper.map(createdOtpEntity, OtpDto.class);
      return returnOtpDto;
    } else {
      throw new OtpServiceException(MessageCode.USER_OTP_SERVICE_BLOCKED.name(),
          Message.USER_OTP_SERVICE_BLOCKED.getMessage(), HttpStatus.FORBIDDEN);
    }
  }

  @Transactional
  @Override
  public void validateOtp(OtpDto otpDto) {
    OtpEntity otpEntity = otpRepository.findByOtpId(otpDto.getOtpId());

    if (utils.findDifferenceBetweenDatesInMinute(otpEntity.getCreationTime(), new Date()) > Long
        .parseLong(appProperties.getProperty("blockDuration"))) {
      throw new OtpServiceException(MessageCode.OTP_CODE_EXPIRED.name(),
          Message.OTP_CODE_EXPIRED.getMessage(), HttpStatus.EXPECTATION_FAILED);
    }
    if (!otpDto.getCode().equals(otpEntity.getCode())) {
      throw new OtpServiceException(MessageCode.OTP_CODE_MISMATCH.name(),
          Message.OTP_CODE_MISMATCH.getMessage(), HttpStatus.EXPECTATION_FAILED);
    }

  }

}
