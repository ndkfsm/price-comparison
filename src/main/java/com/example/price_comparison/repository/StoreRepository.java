package com.example.price_comparison.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.price_comparison.entity.Store;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    // 基本的なCRUD操作は自動で提供される
}