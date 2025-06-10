package com.example.price_comparison.controller;

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
public class PriceInfoController {

    @Autowired
    private PriceInfoRepository priceInfoRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StoreRepository storeRepository;

    // フォーム画面の表示（価格入力）
    @GetMapping("/price/new")
    public String showAddForm(Model model) {
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("stores", storeRepository.findAll());
        return "price/add";  // templates/price/add.html を表示
    }

    // 入力された価格情報の登録
    @PostMapping("/price")
    public String addPriceInfo(
        @RequestParam Long productId,
        @RequestParam Long storeId,
        @RequestParam Integer price
    ) {
        Product product = productRepository.findById(productId).orElseThrow();
        Store store = storeRepository.findById(storeId).orElseThrow();

        PriceInfo priceInfo = new PriceInfo(product, store, price);
        priceInfoRepository.save(priceInfo);

        return "redirect:/products";  // 商品一覧などにリダイレクト
    }
}
