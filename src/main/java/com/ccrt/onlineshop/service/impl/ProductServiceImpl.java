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
import org.springframework.web.multipart.MultipartFile;

import com.ccrt.onlineshop.enums.Message;
import com.ccrt.onlineshop.enums.MessageCode;
import com.ccrt.onlineshop.enums.SortType;
import com.ccrt.onlineshop.enums.SortValue;
import com.ccrt.onlineshop.exceptions.ProductServiceException;
import com.ccrt.onlineshop.io.entity.ProductEntity;
import com.ccrt.onlineshop.io.entity.RatingEntity;
import com.ccrt.onlineshop.io.entity.UserEntity;
import com.ccrt.onlineshop.io.repository.ProductRepository;
import com.ccrt.onlineshop.io.repository.RatingRepository;
import com.ccrt.onlineshop.io.repository.SubCategoryRepository;
import com.ccrt.onlineshop.io.repository.UserRepository;
import com.ccrt.onlineshop.service.ProductService;
import com.ccrt.onlineshop.shared.FileUploadUtil;
import com.ccrt.onlineshop.shared.Utils;
import com.ccrt.onlineshop.shared.dto.ProductDto;

@Service
public class ProductServiceImpl implements ProductService {

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private Utils utils;

  @Autowired
  private FileUploadUtil fileUploadUtil;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private SubCategoryRepository subCategoryRepository;

  @Autowired
  private RatingRepository ratingRepository;

  @Autowired
  private ModelMapper modelMapper;

  @Transactional
  @Override
  public ProductDto createProduct(ProductDto productDto) {

    try {
      ProductEntity productEntity = modelMapper.map(productDto, ProductEntity.class);
      productEntity
          .setUploader(userRepository.findByUserId(productDto.getUploaderUserId()));
      productEntity.setSubCategory(
          subCategoryRepository.findBySubCategoryId(productDto.getSubCategoryId()));
      productEntity.setPrevPrice(productDto.getPrevPrice());
      String productId = utils.generateProductId();
      String imageName = productId + "." + utils.getFileExtension(productDto.getImage().getOriginalFilename());
      fileUploadUtil.saveFile(FileUploadUtil.PRODUCT_UPLOAD_DIR, imageName, productDto.getImage());
      productEntity.setProductId(productId);
      productEntity.setImageUrl("/products/" + imageName);
      ProductEntity createdProductEntity = productRepository.save(productEntity);
      return modelMapper.map(createdProductEntity, ProductDto.class);
    } catch (IOException e) {
      throw new ProductServiceException(MessageCode.FILE_CREATION_ERROR.name(),
          Message.FILE_CREATION_ERROR.getMessage());
    }
  }

  @Override
  public List<ProductDto> retrieveProducts(int page, int limit, SortValue sortBy, SortType sortType,
      String subCategoryId, double startPrice, double endPrice) {
    Sort sort = getSort(sortBy, sortType);
    Page<ProductEntity> productEntityPage;
    if (subCategoryId != null && !subCategoryId.isEmpty()) {
      productEntityPage = productRepository.findAllBySubCategory_SubCategoryId(subCategoryId, startPrice, endPrice,
          PageRequest.of(page, limit, sort));
    } else {
      productEntityPage = productRepository
          .findAll(startPrice, endPrice, PageRequest.of(page, limit, sort));
    }

    List<ProductEntity> productEntities = productEntityPage.getContent();
    List<ProductDto> productDtos = new ArrayList<>();
    for (ProductEntity productEntity : productEntities) {
      productDtos.add(modelMapper.map(productEntity, ProductDto.class));
    }
    return productDtos;
  }

  @Override
  public ProductDto retrieveProduct(String productId) {
    ProductEntity productEntity = productRepository.findByProductId(productId);
    if (productEntity == null) {
      throw new ProductServiceException(MessageCode.PRODUCT_NOT_FOUND.name(), Message.PRODUCT_NOT_FOUND.getMessage(),
          HttpStatus.NOT_FOUND);
    }
    return modelMapper.map(productEntity, ProductDto.class);
  }

  @Override
  public List<ProductDto> searchProducts(int page, int limit, String keyword, double startPrice, double endPrice,
      String subCategoryId, SortValue sortBy, SortType sortType) {
    Page<ProductEntity> productPage;
    Sort sort = getSort(sortBy, sortType);
    if (!subCategoryId.equals("all")) {
      productPage = productRepository.searchByTitleAndCategory(keyword, startPrice, endPrice, subCategoryId,
          PageRequest.of(page, limit, sort));
    } else {
      productPage = productRepository.searchByTitle(keyword, startPrice, endPrice,
          PageRequest.of(page, limit, sort));
    }
    List<ProductEntity> productEntities = productPage.getContent();
    List<ProductDto> productDtos = new ArrayList<>();
    for (ProductEntity productEntity : productEntities) {
      productDtos.add(modelMapper.map(productEntity, ProductDto.class));
    }
    return productDtos;
  }

  private Sort getSort(SortValue sortBy, SortType sortType) {
    Sort sort = Sort.by("price").ascending();
    if (sortBy == SortValue.PRICE) {
      if (sortType == SortType.ASC) {
        sort = Sort.by("price").ascending();
      } else if (sortType == SortType.DESC) {
        sort = Sort.by("price").descending();
      }
    } else if (sortBy == SortValue.CREATION_TIME) {
      if (sortType == SortType.ASC) {
        sort = Sort.by("creationTime").ascending();
      } else if (sortType == SortType.DESC) {
        sort = Sort.by("creationTime").descending();
      }
    }
    return sort;
  }

  @Transactional
  @Override
  public ProductDto updateProduct(String productId, ProductDto productDto) {
    ProductEntity productEntity = productRepository.findByProductId(productId);
    if (productEntity == null) {
      throw new ProductServiceException(MessageCode.PRODUCT_NOT_FOUND.name(), Message.PRODUCT_NOT_FOUND.getMessage(),
          HttpStatus.NOT_FOUND);
    }
    if (productDto.getTitle() != null)
      productEntity.setTitle(productDto.getTitle());
    if (productDto.getDescription() != null)
      productEntity.setDescription(productDto.getDescription());

    if (productDto.getPrice() != 0) {
      productEntity.setPrevPrice(productEntity.getPrice());
      productEntity.setPrice(productDto.getPrice());
    }

    ProductEntity updatedProductEntity = productRepository.save(productEntity);
    return modelMapper.map(updatedProductEntity, ProductDto.class);
  }

  @Transactional
  @Override
  public ProductDto updateImage(String productId, MultipartFile image) {

    try {
      ProductEntity productEntity = productRepository.findByProductId(productId);
      if (productEntity == null) {
        throw new ProductServiceException(MessageCode.PRODUCT_NOT_FOUND.name(), Message.PRODUCT_NOT_FOUND.getMessage(),
            HttpStatus.NOT_FOUND);
      }
      if (image.isEmpty()) {
        throw new ProductServiceException(MessageCode.IMAGE_NOT_VALID.name(), Message.IMAGE_NOT_VALID.getMessage(),
            HttpStatus.NOT_FOUND);
      }
      String imageName = productId + "." + utils.getFileExtension(image.getOriginalFilename());
      fileUploadUtil.saveFile(FileUploadUtil.PRODUCT_UPLOAD_DIR, imageName, image);
      productEntity.setImageUrl(FileUploadUtil.PRODUCT_UPLOAD_DIR + "\\" + imageName);
      ProductEntity createdProductEntity = productRepository.save(productEntity);
      return modelMapper.map(createdProductEntity, ProductDto.class);
    } catch (IOException e) {
      throw new ProductServiceException(MessageCode.FILE_CREATION_ERROR.name(),
          Message.FILE_CREATION_ERROR.getMessage());
    }

  }

  @Transactional
  @Override
  public ProductDto updateStock(String productId, long numEntities) {
    ProductEntity productEntity = productRepository.findByProductId(productId);
    if (productEntity == null) {
      throw new ProductServiceException(MessageCode.PRODUCT_NOT_FOUND.name(), Message.PRODUCT_NOT_FOUND.getMessage(),
          HttpStatus.NOT_FOUND);
    }
    productEntity.setTotalEntities(productEntity.getTotalEntities() + numEntities);
    ProductEntity createdProductEntity = productRepository.save(productEntity);
    return modelMapper.map(createdProductEntity, ProductDto.class);
  }

  @Transactional
  @Override
  public ProductDto addRating(String productId, ProductDto productDto) {
    ProductEntity productEntity = productRepository.findByProductId(productId);
    if (productEntity == null) {
      throw new ProductServiceException(MessageCode.PRODUCT_NOT_FOUND.name(), Message.PRODUCT_NOT_FOUND.getMessage(),
          HttpStatus.NOT_FOUND);
    }
    UserEntity userEntity = userRepository.findByUserId(productDto.getRaterUserId());
    if (userEntity == null) {
      throw new ProductServiceException(MessageCode.USER_NOT_FOUND.name(), Message.USER_NOT_FOUND.getMessage(),
          HttpStatus.NOT_FOUND);
    }
    RatingEntity foundRatingEntity = ratingRepository.findByUser_UserIdAndProduct_ProductId(productDto.getRaterUserId(),
        productId);
    if (foundRatingEntity != null) {
      throw new ProductServiceException(MessageCode.RATING_ALREADY_EXISTS.name(),
          Message.RATING_ALREADY_EXISTS.getMessage(),
          HttpStatus.CONFLICT);
    }
    double newAverageRating = (productEntity.getAverageRating() * productEntity.getTotalRater()
        + productDto.getRating()) / (productEntity.getTotalRater() + 1);
    productEntity.setAverageRating(newAverageRating);
    productEntity.setTotalRater(productEntity.getTotalRater() + 1);
    ProductEntity createdProductEntity = productRepository.save(productEntity);
    RatingEntity ratingEntity = new RatingEntity();
    ratingEntity.setProduct(productEntity);
    ratingEntity.setUser(userEntity);
    ratingEntity.setRating(productDto.getRating());
    ratingRepository.save(ratingEntity);
    return modelMapper.map(createdProductEntity, ProductDto.class);
  }

  @Transactional
  @Override
  public ProductDto updateRating(String productId, ProductDto productDto) {
    ProductEntity productEntity = productRepository.findByProductId(productId);
    if (productEntity == null) {
      throw new ProductServiceException(MessageCode.PRODUCT_NOT_FOUND.name(), Message.PRODUCT_NOT_FOUND.getMessage(),
          HttpStatus.NOT_FOUND);
    }

    RatingEntity foundRatingEntity = ratingRepository.findByUser_UserIdAndProduct_ProductId(productDto.getRaterUserId(),
        productId);
    if (foundRatingEntity == null) {
      throw new ProductServiceException(MessageCode.RATING_NOT_FOUND.name(),
          Message.RATING_NOT_FOUND.getMessage(),
          HttpStatus.CONFLICT);
    }
    double newAverageRating = (productEntity.getAverageRating() * productEntity.getTotalRater()
        + productDto.getRating() - foundRatingEntity.getRating()) / (productEntity.getTotalRater());
    productEntity.setAverageRating(newAverageRating);
    ProductEntity createdProductEntity = productRepository.save(productEntity);
    foundRatingEntity.setRating(productDto.getRating());
    ratingRepository.save(foundRatingEntity);
    return modelMapper.map(createdProductEntity, ProductDto.class);
  }

  @Override
  public double retrieveRating(String productId, String userId) {
    RatingEntity foundRatingEntity = ratingRepository.findByUser_UserIdAndProduct_ProductId(userId,
        productId);
    if (foundRatingEntity == null)
      return 0;
    return foundRatingEntity.getRating();
  }

}
