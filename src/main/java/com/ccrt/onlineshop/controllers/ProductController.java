package com.ccrt.onlineshop.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ccrt.onlineshop.enums.SortType;
import com.ccrt.onlineshop.enums.SortValue;
import com.ccrt.onlineshop.model.request.ProductCreationRequestModel;
import com.ccrt.onlineshop.model.response.ProductRest;
import com.ccrt.onlineshop.service.ProductService;
import com.ccrt.onlineshop.shared.dto.ProductDto;

@RestController
@RequestMapping("products")
public class ProductController {
  @Autowired
  private ProductService productService;

  @Autowired
  private ModelMapper modelMapper;

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ProductRest addProduct(@RequestPart(name = "image") MultipartFile image,
      @ModelAttribute ProductCreationRequestModel productCreationRequestModel, Principal principal) {
    System.out.println(productCreationRequestModel.toString());
    ProductDto productDto = modelMapper.map(productCreationRequestModel, ProductDto.class);
    productDto.setImage(image);
    productDto.setUploaderUserId(principal.getName());
    ProductDto createdProductDto = productService.createProduct(productDto);
    return modelMapper.map(createdProductDto, ProductRest.class);
  }

  @GetMapping
  public List<ProductRest> retrieveProducts(@RequestParam(name = "page", defaultValue = "0", required = false) int page,
      @RequestParam(name = "limit", defaultValue = "15", required = false) int limit,
      @RequestParam(name = "sub-category", defaultValue = "", required = false) String subCategoryId,
      @RequestParam(name = "sortBy", defaultValue = "CREATION_TIME", required = false) SortValue sortBy,
      @RequestParam(name = "sortType", defaultValue = "DESC", required = false) SortType sortType,
      @RequestParam(name = "startPrice", defaultValue = "0", required = false) double startPrice,
      @RequestParam(name = "endPrice", defaultValue = "999999999", required = false) double endPrice) {
    List<ProductDto> productDtos = productService.retrieveProducts(page, limit, sortBy, sortType, subCategoryId,
        startPrice, endPrice);
    List<ProductRest> productRests = new ArrayList<>();
    for (ProductDto productDto : productDtos) {
      productRests.add(modelMapper.map(productDto, ProductRest.class));
    }
    return productRests;
  }

  @GetMapping("/{productId}")
  public ProductRest retrieveProduct(@PathVariable String productId) {
    ProductDto productDto = productService.retrieveProduct(productId);
    return modelMapper.map(productDto, ProductRest.class);
  }
}
