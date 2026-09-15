@product-detail
Feature: Product Detail Page Actions

  Background:
    Given User is logged in as "standard_user"
    And User is on product detail page for "Sauce Labs Backpack"

  @detail-add-to-cart
  Scenario: Add product to cart from product detail page
    When User clicks add to cart button
    Then The shopping cart badge should display "1"
    And The button should change to "Remove"

  @detail-remove-from-cart
  Scenario: Remove product from cart from product detail page
    Given User has already added the product to cart from detail page
    When User clicks remove button
    Then The shopping cart badge should disappear
    And The button should change to "Add to cart"

  @detail-back-to-inventory
  Scenario: Navigate back to inventory page from product detail page
    When User clicks back to products button
    Then User should be redirected to inventory page
