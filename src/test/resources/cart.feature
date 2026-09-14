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
