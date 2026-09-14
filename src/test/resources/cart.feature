@cart
Feature: Cart Page

  Background:
    Given User is logged in as "standard_user"

  Scenario: Verify cart item matches selected product
    When User adds the following products to cart
      | Sauce Labs Backpack |
    And User clicks shopping cart icon
    Then User should be redirected to cart page
    And The cart should display item name "Sauce Labs Backpack" and price "$29.99"

  Scenario: Remove item from cart page
    When User adds the following products to cart
      | Sauce Labs Backpack |
    And User clicks shopping cart icon
    Then User should be redirected to cart page
    And User removes "Sauce Labs Backpack" from cart page
    Then The cart should have no items

  Scenario: Remove multiple items from cart page, remaining item stays
    When User adds the following products to cart
      | Sauce Labs Backpack     |
      | Sauce Labs Bike Light   |
      | Sauce Labs Bolt T-Shirt |
    And User clicks shopping cart icon
    Then User should be redirected to cart page
    And User removes the following products from cart page
      | Sauce Labs Backpack   |
      | Sauce Labs Bike Light |
    Then The cart should display item name "Sauce Labs Bolt T-Shirt" and price "$15.99"

  Scenario: Remove all items from cart page one by one
    When User adds the following products to cart
      | Sauce Labs Backpack     |
      | Sauce Labs Bike Light   |
      | Sauce Labs Bolt T-Shirt |
    And User clicks shopping cart icon
    Then User should be redirected to cart page
    And User removes the following products from cart page
      | Sauce Labs Backpack     |
      | Sauce Labs Bike Light   |
      | Sauce Labs Bolt T-Shirt |
    Then The cart should have no items

  Scenario: Navigate to product detail page by clicking item name on cart page
    When User adds the following products to cart
      | Sauce Labs Backpack |
    And User clicks shopping cart icon
    Then User should be redirected to cart page
    And User clicks on product title "Sauce Labs Backpack"
    Then User should be redirected to product detail page
    And The product details should display name "Sauce Labs Backpack" and price "$29.99"

  Scenario: Navigate back to homepage from cart via continue shopping
    When User clicks shopping cart icon
    Then User should be redirected to cart page
    And User clicks continue shopping button
    Then User should be redirected to inventory page

  @bug @expected-fail
  Scenario: Checkout seharusnya tidak bisa lanjut jika cart kosong
    When User clicks shopping cart icon
    Then User should be redirected to cart page
    And The cart should have no items
    When User clicks checkout button
    Then User should stay on cart page
