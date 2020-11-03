package com.test.demo.model;

public class Product {
	
	
	public Product() {
		super();
	}
	public Product(String name, String price, String productType) {
		super();
		this.name = name;
		this.price = price;
		this.productType = productType;
	}
	private String name;
	private String price;
	private String productType;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	
	@Override
	public String toString() {
		return "Product [name=" + name + ", price=" + price + ", productType=" + productType + "]";
	}
	
}
