package org.example.ProductManager.database;

import org.example.ProductManager.models.Product;
import org.example.ProductManager.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Database {
	private static final Logger logger = LoggerFactory.getLogger(Database.class);
	@Bean
	CommandLineRunner initDatabase(ProductRepository productRepository) {
		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {
				Product productA = new Product("Iphone 13 Pro MaX", 2021, 1500.0, "");
				Product productB = new Product("Iphone 13 Pro MaX", 2022, 1600.0, "");
				logger.info("Insert data: " + productRepository.save(productA));
				logger.info("Insert data: " + productRepository.save(productB));
			}
		};
	}
}
