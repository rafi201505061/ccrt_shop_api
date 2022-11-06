package com.ccrt.onlineshop.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ccrt.onlineshop.enums.SortType;
import com.ccrt.onlineshop.enums.SortValue;
import com.ccrt.onlineshop.shared.dto.ProductDto;

public interface ProductService {
	ProductDto createProduct(ProductDto productDto);

	List<ProductDto> retrieveProducts(int page, int limit, SortValue sortBy, SortType sortType, String subCategoryId,
			double startPrice, double endPrice);

	ProductDto retrieveProduct(String productId);

	List<ProductDto> searchProducts(int page, int limit, String keyword, double startPrice, double endPrice,
			String subCategoryId, SortValue sortBy, SortType sortType);

	ProductDto updateProduct(String productId, ProductDto productDto);

	ProductDto updateImage(String productId, MultipartFile image);

	ProductDto updateStock(String productId, long numEntities);

}
