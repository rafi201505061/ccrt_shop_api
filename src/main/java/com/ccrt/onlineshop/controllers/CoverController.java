package com.ccrt.onlineshop.controllers;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ccrt.onlineshop.enums.CoverType;
import com.ccrt.onlineshop.enums.Message;
import com.ccrt.onlineshop.enums.MessageCode;
import com.ccrt.onlineshop.exceptions.CoverServiceException;
import com.ccrt.onlineshop.model.request.CoverCreationRequestModel;
import com.ccrt.onlineshop.model.response.CoverRest;
import com.ccrt.onlineshop.service.CoverService;
import com.ccrt.onlineshop.shared.dto.CoverDto;

@RestController
@RequestMapping("covers")
public class CoverController {
  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private CoverService coverService;

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public CoverRest createCover(@RequestPart(name = "image", required = false) MultipartFile image,
      @ModelAttribute CoverCreationRequestModel coverCreationRequestModel) {
    checkCoverCreationRequest(coverCreationRequestModel, image);
    CoverDto coverDto = modelMapper.map(coverCreationRequestModel, CoverDto.class);
    coverDto.setImage(image);
    CoverDto createdCoverDto = coverService.createCover(coverDto);
    return modelMapper.map(createdCoverDto, CoverRest.class);
  }

  @DeleteMapping("/{coverId}")
  public ResponseEntity<String> removeCover(@PathVariable long coverId) {
    coverService.removeCover(coverId);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping
  public List<CoverRest> getCovers() {
    List<CoverDto> coverDtos = coverService.retrieveCovers();
    List<CoverRest> coverRests = new ArrayList<>();
    for (CoverDto coverDto : coverDtos) {
      coverRests.add(modelMapper.map(coverDto, CoverRest.class));

    }
    return coverRests;
  }

  private void checkCoverCreationRequest(CoverCreationRequestModel coverCreationRequestModel, MultipartFile image) {
    CoverType type = coverCreationRequestModel.getType();
    String link = coverCreationRequestModel.getLink();
    String itemId = coverCreationRequestModel.getItemId();
    if (type == null) {
      throw new CoverServiceException(MessageCode.INVALID_TYPE.name(), Message.INVALID_TYPE.getMessage(),
          HttpStatus.BAD_REQUEST);
    }
    if (link == null && itemId == null) {
      throw new CoverServiceException(MessageCode.BAD_REQUEST.name(), "You must provide either link or itemId.",
          HttpStatus.BAD_REQUEST);
    }
    if (type == CoverType.OTHERS && (image == null || link == null)) {
      throw new CoverServiceException(MessageCode.BAD_REQUEST.name(),
          "You must provide a valid cover image and valid link for cover type 'OTHERS'.",
          HttpStatus.BAD_REQUEST);
    }
  }
}
