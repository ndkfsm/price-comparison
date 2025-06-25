package com.example.price_comparison.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.price_comparison.entity.PriceInfo;
import com.example.price_comparison.entity.Product;
import com.example.price_comparison.enums.Category;

@Repository
public interface PriceInfoRepository extends JpaRepository<PriceInfo, Long> {
    // 商品別の価格情報を取得するカスタムメソッド
    List<PriceInfo> findByProduct(Product product);
    
    // 商品別の価格情報を価格の安い順で取得
    List<PriceInfo> findByProductOrderByPriceAsc(Product product);

	List<PriceInfo> findByProductCategory(Category category);
}