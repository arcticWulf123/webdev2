package com.jaderoland.webdev2;

import jakarta.validation.constraints.Min;

public class Product {
	private String productName;
	@Min(value = 1, message = "Price should be greater than 0")
	private int price;
	private Long productId;
	private Category category;

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Product(String productName, int price, Long productId, Category category) {
		this.productName = productName;
		this.price = price;
		this.productId = productId;
		this.category = category;
	}

	public Product() {
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}
}
