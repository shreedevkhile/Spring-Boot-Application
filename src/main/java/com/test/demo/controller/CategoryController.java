package com.test.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.test.demo.model.Category;
import com.test.demo.model.Product;

@RestController
public class CategoryController {
	@Autowired
	MongoTemplate mongoTemplate;

	// 1. Add a category
	/*
	 * { "categoryName": "HeathCare", "categoryId": "1", "categorySubType":
	 * "Medicine", "categories": [ { "categoryId": "Medicine1" }, { "categoryId":
	 * "Medicine2" } ] }
	 */
	@PostMapping("/addcategory")
	public ResponseEntity<Category> createCategory(@RequestBody Category category) {
		try {
			return new ResponseEntity<Category>(mongoTemplate.save(category), HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// 2. Add Product mapped to a category or categories.
	/*
	 * { "categoryName":"Heathcare", "product":[ { "name":"Product1",
	 * "price":"2000", "productType":"type1" },{ "name":"Product2", "price":"3000",
	 * "productType":"type2" } ] }
	 */
	@PutMapping("/addproduct")
	public ResponseEntity<Category> updateCategory(@RequestBody Category category) {

		Query query = new Query();
		query.addCriteria(Criteria.where("categoryName").is(category.getCategoryName()));

		if (mongoTemplate.exists(query, Category.class)) {
			Update update = new Update();
			update.set("product", category.getProduct());
			mongoTemplate.updateMulti(query, update, Category.class);
			return new ResponseEntity<Category>(mongoTemplate.findOne(query, Category.class), HttpStatus.OK);
		} else

		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// 3. Get all categories with all its child categories mapped to it.

	@GetMapping("/getallcategory")
	public ResponseEntity<List<Category>> getAllCategory() {
		List<Category> allData = mongoTemplate.findAll(Category.class);
		if (!allData.isEmpty()) {
			return new ResponseEntity<>(allData, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
	//4. Get all products by a category.
/*	{ "categoryName":"Heathcare" }*/
	@GetMapping("/getproductbycategory")
	public ResponseEntity<List<Product>> GetProductByCategory(@RequestBody Category category) {
		
		Query query = new Query();
		query.addCriteria(Criteria.where("categoryName").is(category.getCategoryName()));

		List<Category> allData = mongoTemplate.find(query,Category.class);
		List<Product> listOfProductByCategory= new ArrayList<>();
		if (!allData.isEmpty()) {
			
			allData.forEach(e ->listOfProductByCategory.addAll(e.getProduct()));
			
			return new ResponseEntity<>(listOfProductByCategory, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
	//5.Update product details (name, price etc.)
	
	/*{ "categoryName":"Heathcare",
		"categorySubType":"Medicine",
		"product":[
		    {
		        "name": "Product1",
		        "price": "4000",
		        "productType": "type1"
		    },
		    {
		        "name": "Product2",
		        "price": "3000",
		        "productType": "type2"
		    }
		]
		}*/
	
	@PutMapping("/updateproduct")
	public ResponseEntity<Category> updateProduct(@RequestBody Category category) {

		Query query = new Query();
		query.addCriteria(Criteria.where("categoryName").is(category.getCategoryName()));
		query.addCriteria(Criteria.where("categorySubType").is(category.getCategorySubType()));
		if (mongoTemplate.exists(query, Category.class)) {
			Update update = new Update();
			update.set("product", category.getProduct());
			mongoTemplate.updateFirst(query, update, Category.class);
			return new ResponseEntity<Category>(mongoTemplate.findOne(query, Category.class), HttpStatus.OK);
		} else

		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
}
