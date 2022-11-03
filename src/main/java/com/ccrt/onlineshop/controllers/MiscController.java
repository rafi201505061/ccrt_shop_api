package com.ccrt.onlineshop.controllers;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ccrt.onlineshop.enums.SortType;
import com.ccrt.onlineshop.enums.SortValue;
import com.ccrt.onlineshop.model.response.ProductRest;
import com.ccrt.onlineshop.service.ProductService;
import com.ccrt.onlineshop.shared.dto.ProductDto;

@RestController
@RequestMapping("product-search")
public class MiscController {

  @Autowired
  private ProductService productService;

  @Autowired
  private ModelMapper modelMapper;

  @GetMapping
  public List<ProductRest> searchProducts(@RequestParam(name = "page", defaultValue = "0", required = false) int page,
      @RequestParam(name = "limit", defaultValue = "15", required = false) int limit,
      @RequestParam(name = "keyword", defaultValue = "", required = true) String keyword,
      @RequestParam(name = "startPrice", defaultValue = "0", required = false) double startPrice,
      @RequestParam(name = "endPrice", defaultValue = "999999999", required = false) double endPrice,
      @RequestParam(name = "sub-category", defaultValue = "all", required = false) String subCategoryId,
      @RequestParam(name = "sortBy", defaultValue = "CREATION_TIME", required = false) SortValue sortBy,
      @RequestParam(name = "sortType", defaultValue = "DESC", required = false) SortType sortType) {
    List<ProductDto> productDtos = productService.searchProducts(page, limit, keyword, startPrice, endPrice,
        subCategoryId, sortBy, sortType);
    List<ProductRest> productRests = new ArrayList<>();
    for (ProductDto productDto : productDtos) {
      productRests.add(modelMapper.map(productDto, ProductRest.class));
    }
    return productRests;
  }
}
