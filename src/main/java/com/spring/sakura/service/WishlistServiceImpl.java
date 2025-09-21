package com.spring.sakura.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.sakura.dao.WishlistRepository;
import com.spring.sakura.entities.Products;

@Service
public class WishlistServiceImpl implements IWishlistService{
	
	@Autowired
	private WishlistRepository wishlistRepository;

	@Override
	public int addProductWishlistByUserId(Long userId, Long productId) {
		return wishlistRepository.addProductWishlistByUserId(userId, productId);
		
	}

	@Override
	public int removeProductWishlistByUserId(Long userId, Long productId) {
		return wishlistRepository.removeProductWishlistByUserId(userId, productId);
		
	}

	@Override
	public List<Products> getWishlistProductsByUserId(Long userId) {
		return wishlistRepository.getWishlistProductsByUserId(userId);

	}

}
