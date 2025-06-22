package com.example.price_comparison.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.price_comparison.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // 基本的なCRUD操作は自動で提供される
	Product findByName(String name);
}