package com.spring.sakura.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.spring.sakura.entities.Products;

import jakarta.transaction.Transactional;

public interface ProductRepository extends CrudRepository<Products, Long>{
	
	@Query(value="select * from COL_PRODUCT_DTL", nativeQuery=true)
	public List<Products> getAllProducts();
	
	@Query(value="select * from COL_PRODUCT_DTL where id= :id", nativeQuery=true)
	public Products getProductById(@Param("id") Long id);
	
	@Transactional
	@Modifying
	@Query(value="update COL_PRODUCT_DTL set PRODUCT_NAME = :pname where id= :id", nativeQuery=true)
	public int updateProductNameById(@Param("pname") String name, @Param("id") Long id);
	
	@Transactional
	@Modifying
	@Query(value="update COL_PRODUCT_DTL set PRICE = :price where id= :id", nativeQuery=true)
	public int updateProductPriceById(@Param("price") double price, @Param("id") Long id);
	
	@Transactional
	@Modifying
	@Query(value="update COL_PRODUCT_DTL set PRODUCT_IMAGE_1 = :image1 where id= :id", nativeQuery=true)
	public int updateProductImage1ById(@Param("image1") String image1, @Param("id") Long id);

	@Transactional
	@Modifying
	@Query(value="update COL_PRODUCT_DTL set PRODUCT_IMAGE_2 = :image2 where id= :id", nativeQuery=true)
	public int updateProductImage2ById(@Param("image2") String image2, @Param("id") Long id);
	
}
