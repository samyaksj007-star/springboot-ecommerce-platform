package com.spring.sakura.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.sakura.Exception.ProductNotFoundException;
import com.spring.sakura.dao.ProductRepository;
import com.spring.sakura.entities.Products;

@Service
public class ProductServiceImpl implements IProductService{
	
	@Autowired
	private ProductRepository productRepository;

	@Override
	public List<Products> getAllProducts() {
		List<Products> listProduct = productRepository.getAllProducts();
		return listProduct;
	}

	@Override
	public Products getProductByID(Long id) {
		return productRepository.getProductById(id);
	}

	@Override
	public Products addNewProduct(Products product) {
		 return productRepository.save(product);
	}

	@Override
	public void deleteProductByID(Long id) {
		productRepository.deleteById(id);
		
	}

	@Override
	public Products updateProductNameById(String name, Long id) {
		int updated = productRepository.updateProductNameById(name, id);

	    if (updated > 0) {
	        return productRepository.findById(id)
	                .orElseThrow(() -> new ProductNotFoundException("Product updated but could not be retrieved with ID: " + id));
	    } else {
	        throw new ProductNotFoundException("Product not found or name was not updated for ID: " + id);
	    }
	}

	@Override
	public Products updateProductPriceById(double price, Long id) {
		int updated = productRepository.updateProductPriceById(price, id);

	    if (updated > 0) {
	        return productRepository.findById(id)
	                .orElseThrow(() -> new ProductNotFoundException("Product updated but could not be retrieved with ID: " + id));
	    } else {
	        throw new ProductNotFoundException("Product not found or price was not updated for ID: " + id);
	    }
	}

	@Override
	public Products updateProductImage1ById(String image1, Long id) {
		int updated = productRepository.updateProductImage1ById(image1, id);

	    if (updated > 0) {
	        return productRepository.findById(id)
	                .orElseThrow(() -> new ProductNotFoundException("Product updated but could not be retrieved with ID: " + id));
	    } else {
	        throw new ProductNotFoundException("Product not found or image1 was not updated for ID: " + id);
	    }
	}

	@Override
	public Products updateProductImage2ById(String image2, Long id) {
		int updated = productRepository.updateProductImage2ById(image2, id);

	    if (updated > 0) {
	        return productRepository.findById(id)
	                .orElseThrow(() -> new ProductNotFoundException("Product updated but could not be retrieved with ID: " + id));
	    } else {
	        throw new ProductNotFoundException("Product not found or image2 was not updated for ID: " + id);
	    }
	}
	
}
