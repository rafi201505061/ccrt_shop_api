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
    @Query(value = "SELECT p From ProductEntity p WHERE p.price>=:startPrice AND p.price<=:endPrice")
    Page<ProductEntity> findAll(@Param("startPrice") double startPrice, @Param("endPrice") double endPrice,
            Pageable pageable);

    @Query(value = "SELECT p From ProductEntity p WHERE p.price>=:startPrice AND p.price<=:endPrice AND p.subCategory.subCategoryId=:subCategoryId")
    Page<ProductEntity> findAllBySubCategory_SubCategoryId(@Param("subCategoryId") String subCategoryId,
            @Param("startPrice") double startPrice, @Param("endPrice") double endPrice, Pageable pageable);

    ProductEntity findByProductId(String productId);

    @Query(value = "SELECT * FROM product p WHERE MATCH(title) AGAINST(:keyword) AND price>=:startPrice AND price<=:endPrice", nativeQuery = true, countQuery = "SELECT COUNT(*) FROM product p WHERE MATCH(title) AGAINST(:keyword) AND price>=:startPrice AND price<=:endPrice")
    Page<ProductEntity> searchByTitle(
            @Param("keyword") String keyword, @Param("startPrice") double startPrice,
            @Param("endPrice") double endPrice,
            Pageable pageable);

    @Query(value = "SELECT * FROM product p WHERE MATCH(title) AGAINST(:keyword) AND price>=:startPrice AND price<=:endPrice AND sub_category_id=:subCategoryId", nativeQuery = true, countQuery = "SELECT COUNT(*) FROM product p WHERE MATCH(title) AGAINST(:keyword) AND price>=:startPrice AND price<=:endPrice AND sub_category_id=:subCategoryId")
    Page<ProductEntity> searchByTitleAndCategory(
            @Param("keyword") String keyword, @Param("startPrice") double startPrice,
            @Param("endPrice") double endPrice,
            @Param("subCategoryId") String subCategoryId, Pageable pageable);
}
