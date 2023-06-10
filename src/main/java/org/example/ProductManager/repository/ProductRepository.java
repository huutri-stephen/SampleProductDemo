package org.example.ProductManager.repository;

import org.example.ProductManager.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
