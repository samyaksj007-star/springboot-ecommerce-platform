package com.spring.sakura.service;

import java.util.List;

import com.spring.sakura.entities.Products;

public interface IProductService {
	
	public List<Products> getAllProducts();
	
	public Products getProductByID(Long id);
	
	public Products addNewProduct(Products product);
	
	public void deleteProductByID(Long id);
	
	public Products updateProductNameById(String name, Long id);
	
	public Products updateProductPriceById(double price, Long id);
	
	public Products updateProductImage1ById(String image1, Long id);

	public Products updateProductImage2ById(String image2, Long id);

}
