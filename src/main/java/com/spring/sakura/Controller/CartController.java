package com.spring.sakura.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.sakura.DTO.CartItemDTO;
import com.spring.sakura.dao.ProductRepository;
import com.spring.sakura.entities.Cart;
import com.spring.sakura.entities.Products;
import com.spring.sakura.service.ICartService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	private ICartService cartService;
	
	@Autowired
	private ProductRepository productRepository;
	
	// Add product to cart	
	@PostMapping("/add")
	public Cart addToCart(@RequestBody Cart cart, HttpSession session) {
	    if (cart.getUserId() == null) {
	        // Guest cart → store in session
	    	List<Cart> guestCart = (List<Cart>) session.getAttribute("guestCart");
	        if (guestCart == null) {
	            guestCart = new ArrayList<>();
	        }

	        Optional<Cart> existing = guestCart.stream()
	            .filter(c -> c.getProductId().equals(cart.getProductId()))
	            .findFirst();

	        if (existing.isPresent()) {
	            existing.get().setQuantity(existing.get().getQuantity() + cart.getQuantity());
	        } else {
	            guestCart.add(cart);
	        }

	        session.setAttribute("guestCart", guestCart);
	        return cart;
	    } else {
	        // Logged-in user → DB cart
	        return cartService.addProductToCartByUserId(cart);
	    }
	}
	
	// Remove product from cart
	@DeleteMapping("/remove/{productId}")
	public String removeProductFromCart(@PathVariable("productId") Long productId,
	                                    @RequestParam(value = "userId", required = false) Long userId,
	                                    HttpSession session) {
	    if (userId == null) {
	        // Guest cart
	        List<Cart> guestCart = (List<Cart>) session.getAttribute("guestCart");
	        if (guestCart != null) {
	            guestCart.removeIf(c -> c.getProductId().equals(productId));
	            session.setAttribute("guestCart", guestCart);
	            return "Removed successfully";
	        }
	        return "No such product in guest cart";
	    } else {
	        // Logged in user → DB cart
	        int updated = cartService.removeProductToCartByUserId(productId, userId);
	        return updated > 0 ? "Removed successfully" : "No such product in cart";
	    }
	}

	// Display cart items for a logged in and guest user
	@GetMapping
	public List<?> getCartProducts(@RequestParam(value = "userId", required = false) Long userId,
	                               HttpSession session) {
	    if (userId == null) {
	        // Guest cart
	        //return (List<Cart>) session.getAttribute("guestCart");
	        
	        List<Cart> guestCart = (List<Cart>) session.getAttribute("guestCart");
	        if (guestCart == null) return new ArrayList<>();

	        List<CartItemDTO> enrichedCart = new ArrayList<>();
	        for (Cart item : guestCart) {
	            Products p = productRepository.findById(item.getProductId()).orElse(null);
	            if (p != null) {
	                CartItemDTO dto = new CartItemDTO();
	                dto.setProductId(item.getProductId());
	                dto.setQuantity(item.getQuantity());
	                dto.setName(p.getName());
	                dto.setPrice(p.getPrice());
	                dto.setProductImage("/" + p.getImage1());
	                enrichedCart.add(dto);
	            }
	        }
	        return enrichedCart;
	    } else {
	        // Logged in user → DB cart
	        return cartService.getProductToCartByUserId(userId);
	    }
	}
	
	@PostMapping("/increase")
	public ResponseEntity<?> increaseQuantity(@RequestParam Long productId,@RequestParam Long userId){
		Cart updatedCart = cartService.increaseQuantity(productId, userId);
		return ResponseEntity.ok(updatedCart);
	}

	@PostMapping("/decrease")
	public ResponseEntity<?> decreaseQuantity(@RequestParam Long productId,@RequestParam Long userId){
		Cart updatedCart = cartService.decreaseQuantity(productId, userId);
		return ResponseEntity.ok(updatedCart);
	}
	
}
