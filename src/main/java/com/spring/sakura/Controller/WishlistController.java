package com.spring.sakura.Controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import com.spring.sakura.dao.LoginRepository;
import com.spring.sakura.entities.Products;
import com.spring.sakura.entities.Users;
import com.spring.sakura.service.IProductService;
import com.spring.sakura.service.IWishlistService;

@RestController
public class WishlistController {
	
	@Autowired
	private IWishlistService wishlistService;
	
	@Autowired
	private IProductService productService;
	
	@Autowired
    private LoginRepository loginRepository;
	
	@PostMapping("/addProductInWishlist")
	public ResponseEntity<String> addProductWishlistByUserId(@RequestParam Long userId, @RequestParam Long productId){
		Products product = productService.getProductByID(productId);
	    if (product == null) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
	    }

	    int rowsInserted = wishlistService.addProductWishlistByUserId(userId, productId);
	    if (rowsInserted > 0) {
	        return ResponseEntity.ok("Product added to wishlist");
	    } else {
	        return ResponseEntity.status(HttpStatus.CONFLICT).body("Product already in wishlist");
	    }	
	}
	
	@DeleteMapping("/removeProductInWishlist")
	public ResponseEntity<String> removeProductWishlistByUserId(@RequestParam Long userId, @RequestParam Long productId){
		Products product = productService.getProductByID(productId);
	    if (product == null) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
	    }

	    int rowsDeleted = wishlistService.removeProductWishlistByUserId(userId, productId);
	    if (rowsDeleted > 0) {
	        return ResponseEntity.ok("Product removed from wishlist");
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not in wishlist");
	    }
	}
	
	//Below method is used to get all the list of products which are available in wishlist box.
	@GetMapping("/wishlistProducts")
	public ResponseEntity<List<Products>> getWishlistProducts(Authentication auth) {
	    if (auth == null || !auth.isAuthenticated()) {
	        // Return empty list if not logged in
	        return ResponseEntity.ok(Collections.emptyList());
	    }

	    String username = ((UserDetails) auth.getPrincipal()).getUsername();
	    Users user = loginRepository.findByEmail(username);
	    if (user == null) {
	        throw new RuntimeException("User not found");
	    }

	    List<Products> wishlistProducts = wishlistService.getWishlistProductsByUserId(user.getId());
	    return ResponseEntity.ok(wishlistProducts);
	}


		
}
