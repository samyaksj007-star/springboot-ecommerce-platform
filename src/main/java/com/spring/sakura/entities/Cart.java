package com.spring.sakura.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name="COL_CART_DTL")
public class Cart {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name = "user_id", nullable = false)
	private Long userId;
    
    @Column(name = "product_id", nullable = false)
	private Long productId;
	
	@Column(nullable = false)
	private int quantity =1;
	
	@Column(name = "price_at_added", nullable = false, precision = 10, scale = 2)
	private BigDecimal priceAtAdded;
	
	@Column(name = "total_price", precision = 10, scale = 2)
	private BigDecimal totalPrice;
	
	@Column(name = "discount_applied")
	private int discountApplied;
	
	@Column(name = "session_id", length = 255)
	private String sessionId;
	
	@Column(nullable = false, length = 20)
	private String status= "ACTIVE";
	
	
    // === Lifecycle Hooks ===
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

	public Cart() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Cart(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, Long userId, Long productId, int quantity,
			BigDecimal priceAtAdded, BigDecimal totalPrice, int discountApplied, String sessionId, String status) {
		super();
		this.id = id;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.userId = userId;
		this.productId = productId;
		this.quantity = quantity;
		this.priceAtAdded = priceAtAdded;
		this.totalPrice = totalPrice;
		this.discountApplied = discountApplied;
		this.sessionId = sessionId;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getPriceAtAdded() {
		return priceAtAdded;
	}

	public void setPriceAtAdded(BigDecimal priceAtAdded) {
		this.priceAtAdded = priceAtAdded;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public int getDiscountApplied() {
		return discountApplied;
	}

	public void setDiscountApplied(int discountApplied) {
		this.discountApplied = discountApplied;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Cart [id=" + id + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", userId=" + userId
				+ ", productId=" + productId + ", quantity=" + quantity + ", priceAtAdded=" + priceAtAdded
				+ ", totalPrice=" + totalPrice + ", discountApplied=" + discountApplied + ", sessionId=" + sessionId
				+ ", status=" + status + "]";
	}

}
