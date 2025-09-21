package com.spring.sakura.service;

import java.util.List;

import com.spring.sakura.entities.Products;

public interface IWishlistService {
	
	public int addProductWishlistByUserId(Long userId,Long productId);
	
	public int removeProductWishlistByUserId(Long userId,Long productId);
	
	public List<Products> getWishlistProductsByUserId(Long userId);

}
