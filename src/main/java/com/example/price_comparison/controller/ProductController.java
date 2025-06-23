package com.example.price_comparison.controller;

import java.time.LocalDate;

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
	public String listProducts(Model model) {
		model.addAttribute("priceInfos", priceInfoRepository.findAll());
		return "products/list"; // templates/products/list.html を表示
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
			@RequestParam("registered_date") String registered_date) {
		// 商品を探す or 登録
		Product product = productRepository.findByName(product_name);
		if (product == null) {
			product = new Product(product_name);
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
}
