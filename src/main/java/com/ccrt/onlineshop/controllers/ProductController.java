package com.ccrt.onlineshop.controllers;

import java.security.Principal;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ccrt.onlineshop.enums.Message;
import com.ccrt.onlineshop.enums.MessageCode;
import com.ccrt.onlineshop.enums.SortType;
import com.ccrt.onlineshop.enums.SortValue;
import com.ccrt.onlineshop.enums.UsageStatus;
import com.ccrt.onlineshop.exceptions.ProductServiceException;
import com.ccrt.onlineshop.model.request.ProductCreationRequestModel;
import com.ccrt.onlineshop.model.request.ProductRatingRequestModel;
import com.ccrt.onlineshop.model.request.ProductStockUpdateRequestModel;
import com.ccrt.onlineshop.model.request.ProductUpdateRequestModel;
import com.ccrt.onlineshop.model.response.ProductRest;
import com.ccrt.onlineshop.service.ProductService;
import com.ccrt.onlineshop.shared.Utils;
import com.ccrt.onlineshop.shared.dto.ProductDto;

@RestController
@RequestMapping("products")
public class ProductController {
  @Autowired
  private ProductService productService;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private Utils utils;

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ProductRest addProduct(@RequestPart(name = "image") MultipartFile image,
      @ModelAttribute ProductCreationRequestModel productCreationRequestModel, Principal principal) {
    validateProductCreationRequestModel(productCreationRequestModel);
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
    List<ProductRest> productRests = new ArrayList<>();
    if (endPrice < startPrice || (endPrice <= 0 && startPrice <= 0))
      return productRests;
    List<ProductDto> productDtos = productService.retrieveProducts(page, limit, sortBy, sortType, subCategoryId,
        startPrice, endPrice);
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

  @PutMapping("/{productId}")
  public ProductRest updateProduct(@PathVariable String productId,
      @RequestBody ProductUpdateRequestModel productUpdateRequestModel) {
    validateProductUpdateRequestModel(productUpdateRequestModel);
    ProductDto productDto = modelMapper.map(productUpdateRequestModel, ProductDto.class);
    ProductDto updatedProductDto = productService.updateProduct(productId, productDto);
    return modelMapper.map(updatedProductDto, ProductRest.class);
  }

  @DeleteMapping("/{productId}")
  public ResponseEntity<String> deleteProduct(@PathVariable String productId) {
    productService.deleteProduct(productId);
    return new ResponseEntity<String>("Product has been deleted successfully.", HttpStatus.NO_CONTENT);
  }

  @PutMapping(value = "/{productId}/image", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
  public ProductRest updateProductImage(@PathVariable String productId,
      @RequestPart(required = true, name = "image") MultipartFile image) {
    ProductDto updatedProductDto = productService.updateImage(productId, image);
    return modelMapper.map(updatedProductDto, ProductRest.class);
  }

  @PutMapping(value = "/{productId}/stock")
  public ProductRest updateProductStock(@PathVariable String productId,
      @RequestBody ProductStockUpdateRequestModel productStockUpdateRequestModel) {
    long numEntities = productStockUpdateRequestModel.getNumEntities();
    if (numEntities <= 0)
      throw new ProductServiceException(MessageCode.TOTAL_ENTITIES_NOT_VALID.name(),
          Message.TOTAL_ENTITIES_NOT_VALID.getMessage(),
          HttpStatus.BAD_REQUEST);
    ProductDto updatedProductDto = productService.updateStock(productId, numEntities);
    return modelMapper.map(updatedProductDto, ProductRest.class);
  }

  @PostMapping(value = "/{productId}/rating")
  public ProductRest addRating(@PathVariable String productId,
      @RequestBody ProductRatingRequestModel productRatingRequestModel, Principal principal) {
    if (!principal.getName().equals(productRatingRequestModel.getRaterUserId())) {
      throw new ProductServiceException(MessageCode.FORBIDDEN.name(), Message.FORBIDDEN.getMessage(),
          HttpStatus.FORBIDDEN);
    }
    ProductDto updatedProductDto = productService.addRating(productId,
        modelMapper.map(productRatingRequestModel, ProductDto.class));
    return modelMapper.map(updatedProductDto, ProductRest.class);
  }

  // @PutMapping(value = "/{productId}/rating")
  // public ProductRest updateRating(@PathVariable String productId,
  // @RequestBody ProductRatingRequestModel productRatingRequestModel, Principal
  // principal) {
  // if (!principal.getName().equals(productRatingRequestModel.getRaterUserId()))
  // {
  // throw new ProductServiceException(MessageCode.FORBIDDEN.name(),
  // Message.FORBIDDEN.getMessage(),
  // HttpStatus.FORBIDDEN);
  // }
  // ProductDto updatedProductDto = productService.updateRating(productId,
  // modelMapper.map(productRatingRequestModel, ProductDto.class));
  // return modelMapper.map(updatedProductDto, ProductRest.class);
  // }

  @GetMapping(value = "/{productId}/rating")
  public double retrieveRating(@PathVariable String productId,
      @RequestParam(name = "userId", required = true) String userId, Principal principal) {
    if (!principal.getName().equals(userId)) {
      throw new ProductServiceException(MessageCode.FORBIDDEN.name(), Message.FORBIDDEN.getMessage(),
          HttpStatus.FORBIDDEN);
    }
    return productService.retrieveRating(productId,
        userId);

  }

  public void validateProductCreationRequestModel(ProductCreationRequestModel productCreationRequestModel) {
    String title = productCreationRequestModel.getTitle();
    double price = productCreationRequestModel.getPrice();
    UsageStatus usageStatus = productCreationRequestModel.getUsageStatus();
    String subCategoryId = productCreationRequestModel.getSubCategoryId();
    long totalEntities = productCreationRequestModel.getTotalEntities();

    boolean isTitleValid = utils.isNonNullAndNonEmpty(title);
    boolean isPriceValid = price > 0;
    boolean isUsageStatusValid = usageStatus != null;
    boolean isSubCategoryIdValid = utils.isNonNullAndNonEmpty(subCategoryId);
    boolean isTotalEntitiesValid = totalEntities > 0;
    if (!isTitleValid) {
      throw new ProductServiceException(MessageCode.TITLE_NOT_VALID.name(), Message.TITLE_NOT_VALID.getMessage(),
          HttpStatus.BAD_REQUEST);
    }

    if (!isPriceValid) {
      throw new ProductServiceException(MessageCode.PRICE_NOT_VALID.name(), Message.PRICE_NOT_VALID.getMessage(),
          HttpStatus.BAD_REQUEST);
    }

    if (!isUsageStatusValid) {
      throw new ProductServiceException(MessageCode.USAGE_STATUS_NOT_VALID.name(),
          Message.USAGE_STATUS_NOT_VALID.getMessage(),
          HttpStatus.BAD_REQUEST);
    }

    if (!isSubCategoryIdValid) {
      throw new ProductServiceException(MessageCode.SUB_CATEGORY_NOT_FOUND.name(),
          Message.SUB_CATEGORY_NOT_FOUND.getMessage(),
          HttpStatus.BAD_REQUEST);
    }

    if (!isTotalEntitiesValid) {
      throw new ProductServiceException(MessageCode.TOTAL_ENTITIES_NOT_VALID.name(),
          Message.TOTAL_ENTITIES_NOT_VALID.getMessage(),
          HttpStatus.BAD_REQUEST);
    }
  }

  public void validateProductUpdateRequestModel(ProductUpdateRequestModel productUpdateRequestModel) {
    String title = productUpdateRequestModel.getTitle();
    String description = productUpdateRequestModel.getDescription();
    double price = productUpdateRequestModel.getPrice();
    boolean isTitleValid = utils.isNonNullAndNonEmpty(title);
    boolean isDescriptionValid = utils.isNonNullAndNonEmpty(description);
    boolean isPriceValid = price > 0;
    if (!isTitleValid && !isDescriptionValid && !isPriceValid) {
      throw new ProductServiceException(MessageCode.BAD_REQUEST.name(),
          "You must provide valid value any of the field [title/description/price(positive)] to update.",
          HttpStatus.BAD_REQUEST);
    }
  }
}
