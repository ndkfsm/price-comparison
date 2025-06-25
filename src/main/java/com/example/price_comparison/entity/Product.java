package com.example.price_comparison.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import com.example.price_comparison.enums.Category;

@Entity
public class Product {
    
	//　商品名と商品ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    // コンストラクタ（引数なし）
    public Product() {}
    
    // コンストラクタ（引数あり）
    public Product(String name) {
        this.name = name;
    }
    
    // ゲッター・セッター忘れずに
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    // カテゴリーフィールド
    @Enumerated(EnumType.STRING)  
    private Category category;

    
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    
}