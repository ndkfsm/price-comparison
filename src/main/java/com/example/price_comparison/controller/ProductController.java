package com.example.price_comparison.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.price_comparison.entity.PriceInfo;
import com.example.price_comparison.entity.Product;
import com.example.price_comparison.entity.Store;
import com.example.price_comparison.enums.Category;
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

	// 商品・価格情報一覧を表示（PriceInfoを使う）
	@GetMapping("/products")
	public String listProducts(@RequestParam(name = "category", required = false) String categoryStr, Model model) {
	    List<PriceInfo> priceInfos;

	    if (categoryStr != null && !categoryStr.isEmpty()) {
	        Category category = Category.valueOf(categoryStr);
	        priceInfos = priceInfoRepository.findByProductCategory(category);
	    } else {
	        priceInfos = priceInfoRepository.findAll();
	    }

	    model.addAttribute("priceInfos", priceInfos);
	    return "products/list";
	}

	// 商品登録フォーム画面を表示
	@GetMapping("/products/new")
	public String showAddForm() {
		return "products/add"; // templates/products/add.html を表示
	}

	// 商品を新規登録
	@PostMapping("/products")
	public String addProduct(
			@RequestParam("product_name") String product_name,
			@RequestParam("price") Integer price,
			@RequestParam("store_name") String store_name,
			@RequestParam("registered_date") String registered_date,
			@RequestParam("category") String categoryStr) {
		// 商品を探す or 登録
		Product product = productRepository.findByName(product_name);
		Category category = Category.valueOf(categoryStr);
		if (product == null) {
			product = new Product(product_name);
			product.setCategory(category); 
			productRepository.save(product);
		} else {
		    // 既存の商品でもカテゴリを更新したい場合はここでセットする
		    product.setCategory(category);
		    productRepository.save(product);
		}

		// 店舗を探す or 登録
		Store store = storeRepository.findByName(store_name);
		if (store == null) {
			store = new Store(store_name);
			storeRepository.save(store);
		}

		// 日付変換
		LocalDate date = LocalDate.parse(registered_date);

		// PriceInfo作成・保存
		PriceInfo priceInfo = new PriceInfo(product, store, price);
		priceInfo.setRegisteredDate(date);
		priceInfoRepository.save(priceInfo);

		return "redirect:/products";
	}

	   // 選択した商品を削除し、削除成功フラグをリダイレクトで渡す
    @PostMapping("/products/delete")
    public String deleteSelectedProducts(
            @RequestParam(name = "selectedIds", required = false) Long[] selectedIds,
            RedirectAttributes redirectAttributes
    ) {
        if (selectedIds != null) {
            for (Long id : selectedIds) {
                priceInfoRepository.deleteById(id);
            }
            redirectAttributes.addFlashAttribute("deleteSuccess", true); // ← 削除成功フラグ
        }
        return "redirect:/products";
    }

    		// templates/home.html を表示
    @GetMapping("/")
    public String home() {
        return "home"; 
    }
    
    @GetMapping("/products/table")
    public String showPriceTable(
            @RequestParam(value = "category", required = false) String category,
            Model model
    ) {
        List<PriceInfo> priceInfos = priceInfoRepository.findAll(); // まず全件とる

        // ★ カテゴリ指定があったら、条件に合うものだけ残す
        if (category != null && !category.isEmpty()) {
            priceInfos = priceInfos.stream()
                .filter(info -> info.getProduct().getCategory() != null)
                .filter(info -> info.getProduct().getCategory().name().equals(category))
                .collect(Collectors.toList());
        }

        Set<String> storeNames = new TreeSet<>();
        Map<Product, Map<String, Integer>> tableMap = new LinkedHashMap<>();

        for (PriceInfo info : priceInfos) {
            Product product = info.getProduct();
            String storeName = info.getStore().getName();
            Integer price = info.getPrice();

            storeNames.add(storeName);

            tableMap.putIfAbsent(product, new HashMap<>());
            tableMap.get(product).put(storeName, price);
        }

        model.addAttribute("storeNames", storeNames);
        model.addAttribute("tableMap", tableMap);
        model.addAttribute("selectedCategory", category); // ← 選択状態をHTMLに渡す

        return "products/table";
    }
}
