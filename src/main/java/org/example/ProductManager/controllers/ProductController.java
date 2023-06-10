package org.example.ProductManager.controllers;

import org.example.ProductManager.models.Product;
import org.example.ProductManager.models.ResponseObject;
import org.example.ProductManager.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.management.Query;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/products")
public class ProductController {
	//DI == Dependency Injection
	@Autowired
	private ProductRepository productRepository;

	@GetMapping("")
	List<Product> getAllProduct() {
		return productRepository.findAll();
	}

	@GetMapping("/{id}")
	ResponseEntity<ResponseObject> findById(@PathVariable Long id) {
		Optional<Product> foundProduct = productRepository.findById(id);
		return foundProduct.isPresent() ?
			ResponseEntity.status(HttpStatus.OK).body(
				new ResponseObject("OK", "Query product successfully", foundProduct)
			):
			ResponseEntity.status(HttpStatus.NOT_FOUND).body(
				new ResponseObject("ERROR", "Cannot find product with id = " + id, "")
		);
	}

}
