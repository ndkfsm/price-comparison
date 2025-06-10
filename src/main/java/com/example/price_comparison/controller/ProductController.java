package com.example.price_comparison.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.price_comparison.entity.Product;
import com.example.price_comparison.repository.ProductRepository;

@Controller
public class ProductController {
    
    @Autowired
    private ProductRepository productRepository;
    
    // 商品一覧画面を表示
    @GetMapping("/products")
    public String listProducts(Model model) {
        model.addAttribute("products", productRepository.findAll());
        return "products/list";  // templates/products/list.html を表示
    }
    
    // 商品登録フォーム画面を表示
    @GetMapping("/products/new")
    public String showAddForm() {
    	return "products/add";  // templates/products/add.html を表示
    }
    
    // 商品を新規登録
    @PostMapping("/products")
    public String addProduct(@RequestParam String name) {
        Product product = new Product(name);
        productRepository.save(product);
        return "redirect:/products";  // 登録後、一覧画面にリダイレクト
    }
    
    // トップページ（ルートアクセス）
    @GetMapping("/")
    public String home() {
        return "redirect:/products";  // 商品一覧にリダイレクト
    }
}