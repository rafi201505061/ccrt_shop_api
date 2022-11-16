package com.ccrt.onlineshop.io.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.ccrt.onlineshop.io.entity.CategoryList;
import com.ccrt.onlineshop.io.entity.SubCategoryEntity;

@Repository
public interface SubCategoryRepository extends PagingAndSortingRepository<SubCategoryEntity, Long> {
  List<SubCategoryEntity> findAllByCategory_CategoryIdOrderByTitleDesc(String categoryId);

  SubCategoryEntity findBySubCategoryId(String subCategoryId);

  @Query(value = "SELECT c.category_id as categoryId,c.title as categoryTitle,c.image_url as categoryImageUrl,GROUP_CONCAT(sc.sub_category_id) as subCategoryIds,GROUP_CONCAT(sc.title) as subCategoryTitles,GROUP_CONCAT(sc.image_url) as subCategoryImageUrls FROM category c INNER JOIN sub_category sc ON c.id=sc.category_id GROUP BY c.id ORDER BY c.title ASC", nativeQuery = true)
  List<CategoryList> findCategoryListWithSubcategoryList();
}
