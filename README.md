# Dokumentasi Test Automation — SauceDemo

## Daftar Isi
1. [Overview](#1-overview)
2. [Tech Stack & Arsitektur](#2-tech-stack--arsitektur)
3. [Struktur Project](#3-struktur-project)
4. [Cara Menjalankan Test](#4-cara-menjalankan-test)
5. [Test User yang Dipakai](#5-test-user-yang-dipakai)
6. [Ringkasan Coverage](#6-ringkasan-coverage)
7. [Detail Skenario per Feature](#7-detail-skenario-per-feature)
   - [7.1 Login](#71-login-loginfeature)
   - [7.2 Homepage](#72-homepage-homefeature)
   - [7.3 Cart Page](#73-cart-page-cartfeature)
   - [7.4 Product Detail Page](#74-product-detail-page-productdetailfeature)
   - [7.5 Checkout Flow](#75-checkout-flow-checkoutfeature)
8. [Page Object Reference](#8-page-object-reference)
9. [Known Issues & Catatan Desain](#9-known-issues--catatan-desain)

---

## 1. Overview

Project ini adalah **automated UI test suite** untuk situs demo e-commerce [SauceDemo](https://www.saucedemo.com/), dibangun pakai **Cucumber (BDD)** + **Selenium WebDriver** dengan bahasa Java. Test ditulis dalam format Gherkin (`.feature`) yang bisa dibaca non teknis, terhubung ke implementasi Java lewat Step Definitions dan Page Object Model.

**Total skenario saat ini: 5 feature file, 33 scenario (43 test case kalau Scenario Outline dihitung per baris Examples).**

| Feature | Jumlah Scenario | Jumlah Test Case (termasuk Examples) |
|---|---|---|
| Login | 5 | 5 |
| Homepage | 9 (2 di antaranya Scenario Outline) | 19 |
| Cart Page | 7 | 7 |
| Product Detail Page | 3 | 3 |
| Checkout Flow | 9 | 9 |
| **Total** | **33** | **43** |

---

## 2. Tech Stack & Arsitektur

| Komponen | Teknologi |
|---|---|
| Bahasa | Java |
| Test Framework | JUnit 4 (via `@RunWith(Cucumber.class)`) |
| BDD Framework | Cucumber (`cucumber-java`, `cucumber-junit`) |
| Browser Automation | Selenium WebDriver (ChromeDriver) |
| Design Pattern | Page Object Model (POM) + Cucumber Step Definitions |
| Build Tool | Gradle |

## 3. Struktur Project

```
src/test/
├── resources/
│   ├── login.feature
│   ├── home.feature
│   ├── cart.feature
│   ├── productdetail.feature
│   └── checkout.feature
└── java/
    ├── runner/
    │   └── CucumberTest.java
    ├── base/
    │   └── BaseTest.java
    ├── stepdef/
    │   ├── CucumberHooks.java
    │   ├── LoginStepDef.java
    │   ├── HomeStepDef.java
    │   ├── CartStepDef.java
    │   ├── ProductDetailStepDef.java
    │   └── CheckoutStepDef.java
    └── pages/
        ├── LoginPage.java
        ├── HomePage.java
        ├── CartPage.java
        ├── ProductDetailPage.java
        ├── CheckoutInfoPage.java
        ├── CheckoutOverviewPage.java
        └── CheckoutCompletePage.java
```

---

## 4. Cara Menjalankan Test

### Jalankan semua test
```bash
./gradlew test
```

### Jalankan berdasarkan tag tertentu
Setiap scenario punya tag unik (lihat [Section 7](#7-detail-skenario-per-feature)), bisa di-filter pakai `cucumber.filter.tags`:

```bash
# Jalanin cuma 1 scenario spesifik
./gradlew test -Dcucumber.filter.tags="@cart-remove-all"

# Jalanin semua scenario negative/validasi error
./gradlew test -Dcucumber.filter.tags="@negative"

# Jalanin semua kecuali yang tagged @bug (skip known-bug scenario)
./gradlew test -Dcucumber.filter.tags="not @bug"

# Kombinasi AND — checkout DAN negative
./gradlew test -Dcucumber.filter.tags="@checkout and @negative"
```

### Lihat report hasil test
Setelah run, report tersedia di:
- `build/reports/tests/test/index.html` — report standar Gradle/JUnit
- `build/reports/cucumber-reports.html` — report Cucumber (lebih readable, per-step)
- `build/reports/cucumber-reports.json` — buat integrasi CI/tools lain

---

## 5. Test User yang Dipakai

SauceDemo nyediain beberapa akun demo dengan behavior beda-beda. Yang **udah dipakai** di suite ini:

| Username | Password | Dipakai di | Behavior |
|---|---|---|---|
| `standard_user` | `secret_sauce` | Hampir semua feature (Background) | User normal, semua fitur jalan wajar |
| `locked_out_user` | `secret_sauce` | `login.feature` (`@login-locked-out`) | Login ditolak dengan pesan "locked out" |

**Belum dipakai** :
- `problem_user` — dikenal punya bug tampilan (misal semua gambar produk sama)
- `performance_glitch_user` — delay/lag di beberapa aksi
- `error_user`, `visual_user` — variasi bug lain (tergantung versi SauceDemo)

---

## 6. Ringkasan Coverage

Area yang **sudah** dites end-to-end:
-  Login (positive, invalid credentials, locked-out, empty field)
-  Sorting produk (4 opsi filter)
- Logout & link About
- Integritas gambar produk (unik antar produk & sesuai nama produknya)
- Add/remove produk ke cart — single & multiple, dari homepage maupun dari cart page
- Navigasi antar halaman (home ↔ cart ↔ product detail ↔ checkout)
- Checkout multi-item lengkap (info → overview → complete)
- Validasi form checkout (first name, last name, postal code kosong)
- Cancel checkout (dari info page maupun overview page)
- Kalkulasi harga di order summary (subtotal, tax, total — cross-check matematis)
- Fitur generate PDF (smoke test) & navigasi post-checkout
- 1 known bug SauceDemo didokumentasikan (`@bug @expected-fail`)

---

## 7. Detail Skenario per Feature

### 7.1 Login (`login.feature`)
Tag Feature: `@Login`
Background: *(tidak ada — tiap scenario mulai dari `Given User is on login page`)*

| Tag | Scenario | Alur Singkat |
|---|---|---|
| `@login-success` `@positive` | Success login with valid credentials | Buka login page → input `standard_user`/`secret_sauce` → klik login → **redirect ke `/inventory.html`** |
| `@login-invalid-credentials` `@negative` | Login with invalid credentials | Input username benar, password salah → klik login → error *"Epic sadface: Username and password do not match any user in this service"* |
| `@login-locked-out` `@negative` | Login with locked out user | Login pakai `locked_out_user` → error *"Epic sadface: Sorry, this user has been locked out."* |
| `@login-empty-username` `@boundary` | Login with empty username | Username kosong, password diisi → error *"Epic sadface: Username is required"* |
| `@login-empty-password` `@boundary` | Login with empty password | Username diisi, password kosong → error *"Epic sadface: Password is required"* |

---

### 7.2 Homepage (`home.feature`)
Tag Feature: `@HomePage`
Background: `Given User is logged in as "standard_user"`

| Tag | Scenario | Alur Singkat |
|---|---|---|
| `@sorting` | Verify product sorting functionality *(Scenario Outline, 4 examples)* | Pilih filter (`Name A-Z`, `Name Z-A`, `Price low-high`, `Price high-low`) → cek produk pertama sesuai ekspektasi |
| `@logout` | User log out successfully from homepage | Klik burger menu → klik logout → redirect ke login page |
| `@about` | Verify About link points to correct external URL | Klik burger menu → cek href link "About" = `https://saucelabs.com/` |
| `@image-unique-source` | Verify each product displays a unique image | Ambil `src` semua gambar produk → pastikan tidak ada duplikat |
| `@image-matches-product-name` | Verify each product image matches its own product name | Untuk tiap produk, cari gambarnya lewat `data-test` spesifik produk → pastikan atribut `alt` sama dengan nama produk |
| `@cart-add` | User adds a product to the cart | Add 1 produk → badge cart = `"1"` |
| `@cart-add-multiple` | User adds multiple products to the cart | Add 6 produk sekaligus → badge = `"6"` |
| `@cart-remove` | User removes a product from the cart | Sudah ada 1 produk di cart → remove → badge hilang |
| `@cart-remove-multiple` | User removes multiple products from the cart | Sudah ada 6 produk → remove semua → badge hilang |
| `@cart-remove-one-of-multiple` | User removes 1 product from the cart | Sudah ada 3 produk → remove 1 → badge = `"2"` |
| `@home-to-product-detail` | Verify product details match the homepage selection *(Scenario Outline, 6 examples)* | Klik judul tiap produk → redirect ke detail page → cek nama & harga sesuai |

---

### 7.3 Cart Page (`cart.feature`)
Tag Feature: `@cart`
Background: `Given User is logged in as "standard_user"`

| Tag | Scenario | Alur Singkat |
|---|---|---|
| `@cart-verify-item` | Verify cart item matches selected product | Add 1 produk → buka cart → cek nama+harga item tampil benar |
| `@cart-remove-item` | Remove item from cart page | Add 1 produk → buka cart → remove item itu → cart kosong |
| `@cart-remove-multiple-partial` | Remove multiple items from cart page, remaining item stays | Add 3 produk → buka cart → remove 2 → 1 sisa produk tampil benar (nama+harga) |
| `@cart-remove-all` | Remove all items from cart page one by one | Add 3 produk → buka cart → remove ketiganya → cart kosong |
| `@cart-to-product-detail` | Navigate to product detail page by clicking item name on cart page | Add 1 produk → buka cart → klik nama produk → redirect ke detail page, nama+harga cocok |
| `@cart-continue-shopping` | Navigate back to homepage from cart via continue shopping | Buka cart → klik "Continue Shopping" → redirect ke inventory page |
| `@cart-checkout-empty` `@bug` `@expected-fail` | Checkout seharusnya tidak bisa lanjut jika cart kosong | Buka cart kosong → klik checkout → **seharusnya** tetap di cart page. **Ini known-bug SauceDemo** (lihat [Section 9](#9-known-issues--catatan-desain)) |

---

### 7.4 Product Detail Page (`productdetail.feature`)
Tag Feature: `@product-detail`
Background: `Given User is logged in as "standard_user"` + `Given User is on product detail page for "Sauce Labs Backpack"`

| Tag | Scenario | Alur Singkat |
|---|---|---|
| `@detail-add-to-cart` | Add product to cart from product detail page | Klik "Add to cart" → badge = `"1"`, tombol berubah jadi "Remove" |
| `@detail-remove-from-cart` | Remove product from cart from product detail page | Produk sudah di cart → klik "Remove" → badge hilang, tombol balik jadi "Add to cart" |
| `@detail-back-to-inventory` | Navigate back to inventory page from product detail page | Klik "Back to products" → redirect ke inventory page |

---

### 7.5 Checkout Flow (`checkout.feature`)
Tag Feature: `@checkout`
Background: `Given User is logged in as "standard_user"` + add 3 produk (Backpack, Bike Light, Bolt T-Shirt) ke cart + masuk ke checkout information page

| Tag | Scenario | Alur Singkat |
|---|---|---|
| `@checkout-success` `@positive` | Complete checkout successfully with valid information | Isi form lengkap → continue → overview → finish → complete page → pesan *"Thank you for your order!"* |
| `@generate-pdf` | Generate PDF button does not break the checkout complete page | Selesai checkout → klik "Generate PDF" → pastikan halaman tidak error/pindah (smoke test) |
| `@back-to-products` | Navigate back to inventory page from checkout complete page | Selesai checkout → klik "Back to products" → redirect ke inventory page |
| `@order-summary` | Order summary displays correct items and pricing for multiple products | Sampai overview page → cek 3 item (nama+harga) tampil benar → **subtotal = jumlah harga item** → **total = subtotal + tax** |
| `@checkout-empty-firstname` `@negative` | Cannot continue checkout with empty first name | First name kosong → error *"Error: First Name is required"* |
| `@checkout-empty-lastname` `@negative` | Cannot continue checkout with empty last name | Last name kosong → error *"Error: Last Name is required"* |
| `@checkout-empty-postalcode` `@negative` | Cannot continue checkout with empty postal code | Postal code kosong → error *"Error: Postal Code is required"* |
| `@cancel` | Cancel from checkout information page returns to cart | Klik "Cancel" di info page → redirect ke cart page |
| `@cancel-overview` | Cancel from checkout overview page returns to inventory page | Lanjut sampai overview page → klik "Cancel" → redirect ke **inventory page** (beda dari cancel di info page yang ke cart) |

---

## 8. Page Object Reference

| Page Object | Halaman | Method Penting |
|---|---|---|
| `LoginPage` | Login | `openLoginPage()`, `enterUsername()`, `enterPassword()`, `clickLogin()`, `getErrorMessage()` |
| `HomePage` | Inventory/Homepage | `selectSortOption()`, `getFirstItemName()`, `clickAddToCartByProductName()`, `addMultipleProductsToCart()`, `clickRemoveByProductName()`, `removeMultipleProductsFromCart()`, `getCartBadgeCount()`, `clickProductTitleByName()`, `getAllProductImageSrcs()`, `getAllProductNames()`, `getProductImageAltByName()`, `clickBurgerMenu()`, `clickLogout()`, `getAboutLinkHref()` |
| `CartPage` | Cart | `getCartItems()`, `clickRemoveByProductName()`, `removeMultipleProductsFromCart()`, `clickCheckout()`, `clickContinueShopping()`, `isCartEmpty()` |
| `ProductDetailPage` | Product Detail | `getProductName()`, `getProductPrice()`, `getProductImageAlt()`, `clickAddToCart()`, `clickRemove()`, `getActionButtonText()`, `clickBackToProducts()` |
| `CheckoutInfoPage` | Checkout Step 1 (Info) | `enterFirstName()`, `enterLastName()`, `enterPostalCode()`, `clickContinue()`, `clickCancel()`, `getErrorMessage()` |
| `CheckoutOverviewPage` | Checkout Step 2 (Overview) | `clickFinish()`, `clickCancel()`, `getOrderItems()`, `getSubtotalAmount()`, `getTaxAmount()`, `getTotalAmount()` |
| `CheckoutCompletePage` | Checkout Complete | `getCompleteHeaderText()`, `clickGeneratePdf()`, `clickBackToProducts()` |

---

## 9. Known Issues & Catatan Desain

### 9.1 Bug SauceDemo: Checkout dengan cart kosong (`@bug @expected-fail`)
SauceDemo **tidak memvalidasi** cart kosong saat checkout — tombol "Checkout" tetap bisa diklik dan meng-redirect ke `checkout-step-one.html` meskipun cart kosong. Scenario `Checkout seharusnya tidak bisa lanjut jika cart kosong` sengaja ditulis dengan ekspektasi "seharusnya" (bahwa user tetap di cart page), sehingga scenario ini **akan selalu FAILED** sampai bug-nya diperbaiki oleh SauceDemo.

> **Dampak ke CI/build**: Tag `@expected-fail` di sini **murni dokumentasi** — Cucumber/JUnit tidak otomatis menganggapnya "boleh gagal". Build tetap **FAILED** walau kegagalan ini memang yang diharapkan.

### 9.2 Generate PDF — smoke test, bukan verifikasi isi
`generate-pdf-order` men-trigger **download file**, bukan render elemen di halaman. Verifikasi isi PDF (apakah datanya benar) di luar scope Selenium standar tanpa setup tambahan (baca folder downloads, konfigurasi Chrome prefs). Scenario `@generate-pdf` saat ini cuma **smoke test**: klik tombol → pastikan halaman tidak error/berpindah.

### 9.3 Deskripsi produk (`inventory_item_desc`) sengaja tidak divalidasi karena:
- Teks deskripsi panjang → rawan typo kalau di hardcode di feature file
- Kalau SauceDemo mengubah kalimat deskripsi, test akan false-fail meski bukan bug

