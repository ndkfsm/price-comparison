# 🚀 プロジェクト名（例: Price Comparison App）

# Price Comparison App

![GitHub top language](https://img.shields.io/github/languages/top/ndkfsm/price-comparison)
![GitHub repo size](https://img.shields.io/github/repo-size/ndkfsm/price-comparison)

## 概要

このアプリは、複数の店舗の商品価格を比較し、最もお得な価格を素早く見つけるためのツールです。ユーザーは商品を登録し、最新の価格情報を一覧で確認できます。

## デモ

※画面イメージ（例）
![screenshot](./image/top.png)

## 主な機能

- 商品の登録・編集・削除
- 店舗ごとの価格比較
- 登録日の履歴管理
- 最安値のハイライト表示
- Webブラウザで簡単操作

## ディレクトリ構成

```bash
├─main
│  ├─java
│  │  └─com
│  │      └─example
│  │          └─price_comparison
│  │              ├─controller
│  │              ├─entity
│  │              ├─enums
│  │              └─repository
│  └─resources
│      ├─static
│      └─templates
│          └─products
```

## 使用技術

### フロントエンド
- HTML / CSS
- Thymeleaf

### バックエンド
- Spring Boot（Java 17）
- Spring MVC / Spring Data JPA

### データベース
- H2 Database（開発用組み込みDB）

### ビルド / 実行環境
- Maven
- Git / GitHub

### その他
- Lombok（ボイラープレートコード削減）
- JUnit（テストフレームワーク）
