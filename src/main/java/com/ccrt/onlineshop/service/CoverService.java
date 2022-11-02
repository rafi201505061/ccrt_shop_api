package com.ccrt.onlineshop.service;

import java.util.List;

import com.ccrt.onlineshop.shared.dto.CoverDto;

public interface CoverService {
  CoverDto createCover(CoverDto coverDto);

  List<CoverDto> retrieveCovers();
}
