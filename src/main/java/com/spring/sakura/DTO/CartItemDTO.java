package com.spring.sakura.DTO;

import java.math.BigDecimal;

public class CartItemDTO {

    private Long productId;
    private String name;     // for display
    private Double price;    // unit price
    private int quantity;
    private String productImage;
    private BigDecimal priceAtAdded;
    private BigDecimal totalPrice;
    
    public CartItemDTO() {}

    public CartItemDTO(Long productId, String name, Double price, int quantity, String productImage,
			BigDecimal priceAtAdded, BigDecimal totalPrice) {
		super();
		this.productId = productId;
		this.name = name;
		this.price = price;
		this.quantity = quantity;
		this.productImage = productImage;
		this.priceAtAdded = priceAtAdded;
		this.totalPrice = totalPrice;
	}



	// Getters and setters
    public Long getProductId() {
        return productId;
    }
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

	public String getProductImage() {
		return productImage;
	}

	public void setProductImage(String productImage) {
		this.productImage = productImage;
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
    
}