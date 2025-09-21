package com.spring.sakura.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spring.sakura.entities.Cart;

import jakarta.transaction.Transactional;

public interface CartRepository extends JpaRepository<Cart,Long>{
	
	public Cart findByUserIdAndProductIdAndStatus(Long userId, Long productId, String status);
	
	@Transactional
	@Modifying
	@Query(value= "UPDATE COL_CART_DTL SET STATUS = 'INACTIVE' WHERE product_id = :productId and user_id = :userId", nativeQuery = true)
	public int removeProductToCartByUserId(@Param("productId") Long productId, @Param("userId") Long userId);
	
	// Return cart with total_price as well
	@Query(value = "SELECT p.id as productId, p.PRODUCT_NAME as name, p.price as price, " +
            "c.quantity as quantity, p.PRODUCT_IMAGE_1 as productImage, " +
            "c.price_at_added as priceAtAdded, c.total_price as totalPrice " +
            "FROM col_product_dtl p " +
            "JOIN col_cart_dtl c ON p.id = c.product_id " +
            "WHERE c.user_id = :userId AND c.status = 'ACTIVE'",
    nativeQuery = true)
    public List<Object[]> getCartProductsWithQuantity(@Param("userId") Long userId);

    // Keep total_price updated when quantity increases
    @Transactional
    @Modifying
    @Query(value="UPDATE COL_CART_DTL " +
                 "SET QUANTITY = QUANTITY + 1, " +
                 "    TOTAL_PRICE = (QUANTITY + 1) * PRICE_AT_ADDED " +
                 "WHERE product_id = :productId AND user_id = :userId AND status='ACTIVE'",
           nativeQuery = true)
    public int increaseQuantity(@Param("productId") Long productId, @Param("userId") Long userId);

    // Keep total_price updated when quantity decreases
    @Transactional
    @Modifying
    @Query(value="UPDATE COL_CART_DTL " +
                 "SET QUANTITY = QUANTITY - 1, " +
                 "    TOTAL_PRICE = (QUANTITY - 1) * PRICE_AT_ADDED " +
                 "WHERE product_id = :productId AND user_id = :userId AND status='ACTIVE' AND QUANTITY > 1",
           nativeQuery = true)
    public int decreaseQuantity(@Param("productId") Long productId, @Param("userId") Long userId);

    // Query to get full cart total but we are not using as total is fetched in cart.js loadCart() function.
//    @Query(value="SELECT COALESCE(SUM(total_price),0) " +
//                 "FROM COL_CART_DTL WHERE user_id = :userId AND status = 'ACTIVE'",
//           nativeQuery = true)
//    public Double getCartTotal(@Param("userId") Long userId);

}
