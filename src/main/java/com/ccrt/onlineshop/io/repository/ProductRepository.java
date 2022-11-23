package com.ccrt.onlineshop.io.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ccrt.onlineshop.io.entity.ProductEntity;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<ProductEntity, Long> {
    @Query(value = "SELECT p From ProductEntity p WHERE p.price>=:startPrice AND p.price<=:endPrice AND p.isValid=true")
    Page<ProductEntity> findAll(@Param("startPrice") double startPrice, @Param("endPrice") double endPrice,
            Pageable pageable);

    @Query(value = "SELECT p From ProductEntity p WHERE p.price>=:startPrice AND p.price<=:endPrice AND p.subCategory.subCategoryId=:subCategoryId AND p.isValid=true")
    Page<ProductEntity> findAllBySubCategory_SubCategoryId(@Param("subCategoryId") String subCategoryId,
            @Param("startPrice") double startPrice, @Param("endPrice") double endPrice, Pageable pageable);

    @Query(value = "SELECT p From ProductEntity p WHERE p.productId=:productId AND p.isValid=true")
    ProductEntity findByProductId(@Param("productId") String productId);

    @Query(value = "SELECT * FROM product p WHERE MATCH(title) AGAINST(:keyword) AND price>=:startPrice AND price<=:endPrice AND is_valid=true", nativeQuery = true, countQuery = "SELECT COUNT(*) FROM product p WHERE MATCH(title) AGAINST(:keyword) AND price>=:startPrice AND price<=:endPrice")
    Page<ProductEntity> searchByTitle(
            @Param("keyword") String keyword, @Param("startPrice") double startPrice,
            @Param("endPrice") double endPrice,
            Pageable pageable);

    @Query(value = "SELECT * FROM product p WHERE MATCH(title) AGAINST(:keyword) AND price>=:startPrice AND price<=:endPrice AND sub_category_id=:subCategoryId AND is_valid=true", nativeQuery = true, countQuery = "SELECT COUNT(*) FROM product p WHERE MATCH(title) AGAINST(:keyword) AND price>=:startPrice AND price<=:endPrice AND sub_category_id=:subCategoryId")
    Page<ProductEntity> searchByTitleAndCategory(
            @Param("keyword") String keyword, @Param("startPrice") double startPrice,
            @Param("endPrice") double endPrice,
            @Param("subCategoryId") long subCategoryId, Pageable pageable);
}
