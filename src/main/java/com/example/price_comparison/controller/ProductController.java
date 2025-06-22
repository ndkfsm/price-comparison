package com.example.price_comparison.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.price_comparison.entity.PriceInfo;
import com.example.price_comparison.entity.Product;
import com.example.price_comparison.entity.Store;
import com.example.price_comparison.repository.PriceInfoRepository;
import com.example.price_comparison.repository.ProductRepository;
import com.example.price_comparison.repository.StoreRepository;

@Controller
public class ProductController {
    
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PriceInfoRepository priceInfoRepository;

    @Autowired
    private StoreRepository storeRepository;
    
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
    public String addProduct(
    		@RequestParam("product_name") String product_name,
    		@RequestParam("price") Integer price,
    		@RequestParam("store_name") String store_name,
    		@RequestParam("registered_date") String registered_date
	) {    	
    	 	// 1. 商品を探す（なければ登録）
        Product product = productRepository.findByName(product_name);
        if (product == null) {
            product = new Product(product_name);
            productRepository.save(product);
        }
        
        // 2. 店舗を探す（なければ登録）
        Store store = storeRepository.findByName(store_name);
        if (store == null) {
            store = new Store(store_name);
            storeRepository.save(store);
        }

		// 3. 日付をLocalDateに変換
		LocalDate date = LocalDate.parse(registered_date);
		
		// 4. PriceInfoを作って保存
	    PriceInfo priceInfo = new PriceInfo(product, store, price);
	    priceInfo.setRegisteredDate(date);
	    priceInfoRepository.save(priceInfo);
		
		// 登録後、一覧画面にリダイレクト
        return "redirect:/products";
    }
    
    // トップページ（ルートアクセス）
    @GetMapping("/")
    public String home() {
        return "redirect:/products";  // 商品一覧にリダイレクト
    }
}