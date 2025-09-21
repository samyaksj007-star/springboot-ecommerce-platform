package com.spring.sakura.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.spring.sakura.DTO.CartItemDTO;
import com.spring.sakura.dao.CartRepository;
import com.spring.sakura.dao.ProductRepository;
import com.spring.sakura.entities.Cart;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class CartServiceImpl implements ICartService{
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public Cart addProductToCartByUserId(Cart cart) {
	    Cart existingCart = cartRepository.findByUserIdAndProductIdAndStatus(
	            cart.getUserId(), cart.getProductId(), "ACTIVE");

	    if (existingCart != null) {
	        // Increase quantity if already exists
	        existingCart.setQuantity(existingCart.getQuantity() + cart.getQuantity());

	        // Recalculate total price
	        BigDecimal totalPrice = existingCart.getPriceAtAdded()
	                .multiply(BigDecimal.valueOf(existingCart.getQuantity()));
	        existingCart.setTotalPrice(totalPrice);

	        return cartRepository.save(existingCart);
	    } else {
	        // Ensure priceAtAdded is set when new cart item is created
	        if (cart.getPriceAtAdded() == null) {
	            double productPrice = productRepository.findById(cart.getProductId())
	                    .orElseThrow(() -> new RuntimeException("Product not found"))
	                    .getPrice(); // returns double
	            cart.setPriceAtAdded(BigDecimal.valueOf(productPrice));
	        }

	        // Calculate total price
	        BigDecimal totalPrice = cart.getPriceAtAdded()
	                .multiply(BigDecimal.valueOf(cart.getQuantity()));
	        cart.setTotalPrice(totalPrice);

	        return cartRepository.save(cart);
	    }
	}


	@Override
	public int removeProductToCartByUserId(Long productId, Long userId) {
	    return cartRepository.removeProductToCartByUserId(productId, userId);
	}

	@Override
	public List<CartItemDTO> getProductToCartByUserId(Long userId) {
	    List<Object[]> results = cartRepository.getCartProductsWithQuantity(userId);

	    List<CartItemDTO> cartItems = new ArrayList<>();
	    for (Object[] row : results) {
	        CartItemDTO dto = new CartItemDTO();

	        dto.setProductId(((Number) row[0]).longValue());
	        dto.setName((String) row[1]);
	        dto.setPrice(((Number) row[2]).doubleValue());
	        dto.setQuantity(((Number) row[3]).intValue());
	        dto.setProductImage("/" + (String) row[4]);
	        dto.setPriceAtAdded(row[5] != null ? ((BigDecimal) row[5]) : null);
	        dto.setTotalPrice(row[6] != null ? ((BigDecimal) row[6]) : null);

	        cartItems.add(dto);
	    }
	    return cartItems;
	}

	@Override
	public Cart increaseQuantity(Long productId, Long userId) {
	    cartRepository.increaseQuantity(productId, userId);
	    Cart cart = cartRepository.findByUserIdAndProductIdAndStatus(userId, productId, "ACTIVE");

	    if (cart != null) {
	        BigDecimal totalPrice = cart.getPriceAtAdded()
	                .multiply(BigDecimal.valueOf(cart.getQuantity()));
	        cart.setTotalPrice(totalPrice);
	        return cartRepository.save(cart);
	    }
	    return null;
	}

	@Override
	public Cart decreaseQuantity(Long productId, Long userId) {
	    cartRepository.decreaseQuantity(productId, userId);
	    Cart cart = cartRepository.findByUserIdAndProductIdAndStatus(userId, productId, "ACTIVE");

	    if (cart != null) {
	        if (cart.getQuantity() <= 0) {
	            cartRepository.delete(cart);
	            return null;
	        }

	        BigDecimal totalPrice = cart.getPriceAtAdded()
	                .multiply(BigDecimal.valueOf(cart.getQuantity()));
	        cart.setTotalPrice(totalPrice);
	        return cartRepository.save(cart);
	    }
	    return null;
	}
	
//	@Override
//	public Cart addProductToCartByUserId(Cart cart) {
//		Cart existingCart = cartRepository.findByUserIdAndProductIdAndStatus(cart.getUserId(), cart.getProductId(), "ACTIVE");
//
//        if (existingCart != null) {
//            // Increase quantity if already exists
//            existingCart.setQuantity(existingCart.getQuantity() + cart.getQuantity());
//            return cartRepository.save(existingCart);
//        } else {
//            // Insert new cart row
//            return cartRepository.save(cart);
//        }
//	}
//
//
//	@Override
//	public int removeProductToCartByUserId(Long productId, Long userId) {
//		return cartRepository.removeProductToCartByUserId(productId, userId);
//	}
//	
//	@Override
//	public List<CartItemDTO> getProductToCartByUserId(Long userId) {
//	    List<Object[]> results = cartRepository.getCartProductsWithQuantity(userId);
//
//	    List<CartItemDTO> cartItems = new ArrayList<>();
//	    for (Object[] row : results) {
//	        CartItemDTO dto = new CartItemDTO();
//	        
//	        dto.setProductId(((Number) row[0]).longValue());
//	        dto.setName((String) row[1]);
//	        dto.setPrice(((Number) row[2]).doubleValue());
//	        dto.setQuantity(((Number) row[3]).intValue());
//	        //dto.setProductImage((String) row[4]);
//	        dto.setProductImage("/" + (String) row[4]);
//	        
//	        cartItems.add(dto);
//	    }
//	    return cartItems;
//	}
//
//
//	@Override
//	public Cart increaseQuantity(Long productId, Long userId) {
//		cartRepository.increaseQuantity(productId, userId);
//		return cartRepository.findByUserIdAndProductIdAndStatus(userId, productId, "ACTIVE");
//		
//	}
//
//
//	@Override
//	public Cart decreaseQuantity(Long productId, Long userId) {
//		cartRepository.decreaseQuantity(productId, userId);
//		Cart cart = cartRepository.findByUserIdAndProductIdAndStatus(userId, productId, "ACTIVE");
//		// Optional: handle if quantity reaches 0
//	    if (cart != null && cart.getQuantity() <= 0) {
//	        cartRepository.delete(cart);
//	        return null;
//	    }
//	    return cart;
//	}
	
}
