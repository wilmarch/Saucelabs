@product-detail
Feature: Product Detail Page Actions

  Background:
    Given User is on login page
    When User inputs username "standard_user" and password "secret_sauce"
    And User clicks login button
    And User clicks on product title "Sauce Labs Backpack"
    Then User should be redirected to product detail page

  Scenario: Add product to cart from product detail page
    When User clicks add to cart button
    Then The shopping cart badge should display "1"
    And The button should change to "Remove"

  Scenario: Remove product from cart from product detail page
    Given User has already added the product to cart from detail page
    When User clicks remove button
    Then The shopping cart badge should disappear
    And The button should change to "Add to cart"

  Scenario: Navigate back to inventory page from product detail page
    When User clicks back to products button
    Then User should be redirected to inventory page