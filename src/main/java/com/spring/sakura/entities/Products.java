package com.spring.sakura.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name="COL_PRODUCT_DTL")
public class Products {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name ="PRODUCT_NAME")
	private String name;
	
	@Column(name ="PRICE")
	private double price;
	
	@Column(name ="PRODUCT_IMAGE_1")
	private String image1;
	
	@Column(name ="PRODUCT_IMAGE_2")
	private String image2;
	
	@Transient
	private boolean inWishlist;
	
	@ElementCollection
	@CollectionTable(name = "product_fragrances", joinColumns = @JoinColumn(name = "product_id"))
	@MapKeyColumn(name = "fragrance_name")
	@Column(name = "image_url")
	private Map<String, String> fragrance = new HashMap<>();

    @Column(length = 2000)
    private String description;

    private String dimensions;
    
    @ElementCollection
    @CollectionTable(name = "product_images", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "image_url")
    private List<String> images = new ArrayList<>();

	public Products() {
		super();
	}

	public Products(Long id, String name, double price, String image1, String image2, Map<String, String> fragrance,
			String description, String dimensions, List<String> images) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.image1 = image1;
		this.image2 = image2;
		this.fragrance = fragrance;
		this.description = description;
		this.dimensions = dimensions;
		this.images = images;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImage1() {
		return image1;
	}

	public void setImage1(String image1) {
		this.image1 = image1;
	}

	public String getImage2() {
		return image2;
	}

	public void setImage2(String image2) {
		this.image2 = image2;
	}

	public boolean isInWishlist() {
		return inWishlist;
	}

	public void setInWishlist(boolean inWishlist) {
		this.inWishlist = inWishlist;
	}

	public Map<String, String> getFragrance() {
		return fragrance;
	}

	public void setFragrance(Map<String, String> fragrance) {
		this.fragrance = fragrance;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDimensions() {
		return dimensions;
	}

	public void setDimensions(String dimensions) {
		this.dimensions = dimensions;
	}

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

	@Override
	public String toString() {
		return "Products [id=" + id + ", name=" + name + ", price=" + price + ", image1=" + image1 + ", image2="
				+ image2 + ", wishlist=" + inWishlist + ", fragrance=" + fragrance + ", description=" + description
				+ ", dimensions=" + dimensions + ", images=" + images + "]";
	}

}
