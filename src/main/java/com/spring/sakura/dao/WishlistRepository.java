package com.spring.sakura.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spring.sakura.entities.Products;
import com.spring.sakura.entities.Wishlist;

import jakarta.transaction.Transactional;

public interface WishlistRepository extends JpaRepository<Wishlist, Long>{
	
	@Transactional
	@Modifying
	@Query(value="INSERT IGNORE INTO COL_WISHLIST_DTL (user_id, product_id, created_at) VALUES (:userId, :productId, CURRENT_TIMESTAMP)", nativeQuery=true)
	public int addProductWishlistByUserId(@Param("userId") Long userId, @Param("productId") Long productId);

	
	@Transactional
	@Modifying
	@Query(value="DELETE FROM COL_WISHLIST_DTL WHERE user_id = :userId AND product_id = :productId", nativeQuery=true)
	public int removeProductWishlistByUserId(@Param("userId") Long userId, @Param("productId") Long productId);
	
	@Query(value="SELECT p.* FROM col_product_dtl p JOIN col_wishlist_dtl w ON p.id = w.product_id WHERE w.user_id = :userId", nativeQuery=true)
	public List<Products> getWishlistProductsByUserId(@Param("userId") Long userId);

}
