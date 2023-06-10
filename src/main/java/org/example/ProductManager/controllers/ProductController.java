package org.example.ProductManager.controllers;

import org.example.ProductManager.models.Product;
import org.example.ProductManager.models.ResponseObject;
import org.example.ProductManager.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

	@PostMapping("/insert")
	ResponseEntity<ResponseObject> insertProduct(@RequestBody Product newProduct) {
		List<Product> foundProduct = productRepository.findByProductName(newProduct.getProductName().trim());
		if(foundProduct.size() > 0) {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
					new ResponseObject("ERROR", "Product name already taken", "")
			);
		}

		return ResponseEntity.status(HttpStatus.OK).body(
				new ResponseObject("OK", "Insert product successfully", productRepository.save(newProduct))
		);
	}

	@PutMapping("/{id}")
	ResponseEntity<ResponseObject> insertProduct(@RequestBody Product newProduct, @PathVariable Long id) {
		Product updateProduct = productRepository.findById(id)
				.map(product -> {
					product.setProductName(newProduct.getProductName());
					product.setYear(newProduct.getYear());
					product.setPrice(newProduct.getPrice());
					product.setUrl(newProduct.getUrl());
					return productRepository.save(product);
				}).orElseGet(() -> {
					newProduct.setId(id);
					return productRepository.save(newProduct);
				});
		return ResponseEntity.status(HttpStatus.OK).body(
				new ResponseObject("OK", "Update product successfully", updateProduct)
		);
	}

	@DeleteMapping("/{id}")
	ResponseEntity<ResponseObject> deleteProduct(@PathVariable Long id) {
		boolean exists = productRepository.existsById(id);
		if(!exists) {
			productRepository.deleteById(id);
			return ResponseEntity.status(HttpStatus.OK).body(
					new ResponseObject("OK", "Delete product successfully", "")
			);
		} else  {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
					new ResponseObject("ERROR", "Delete product fail", "")
			);
		}
	}
}
