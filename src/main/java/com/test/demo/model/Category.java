package com.test.demo.model;

import java.util.List;
public class Category {
	
	private String categoryName;
	private String categorySubType;
	private List<Categories> categories;
	private List<Product> product;
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getCategorySubType() {
		return categorySubType;
	}
	public void setCategorySubType(String categorySubType) {
		this.categorySubType = categorySubType;
	}
	public List<Categories> getCategories() {
		return categories;
	}
	public void setCategories(List<Categories> categories) {
		this.categories = categories;
	}
	public List<Product> getProduct() {
		return product;
	}
	public void setProduct(List<Product> product) {
		this.product = product;
	}
	
	
	
}
