package com.spring.sakura.service;

import java.util.List;

import com.spring.sakura.DTO.CartItemDTO;
import com.spring.sakura.entities.Cart;

public interface ICartService {
	
	public Cart addProductToCartByUserId(Cart cart);
	
	public int removeProductToCartByUserId(Long productId, Long userId);
	
	public List<CartItemDTO> getProductToCartByUserId(Long userId);
	
	public Cart increaseQuantity(Long productId, Long userId);
	
	public Cart decreaseQuantity(Long productId, Long userId);

}
