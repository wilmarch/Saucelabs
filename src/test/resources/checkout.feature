@checkout
Feature: Checkout Flow

  Background:
    Given User is logged in as "standard_user"
    And User has "Sauce Labs Backpack" in the cart
    And User has "Sauce Labs Bike Light" in the cart
    And User has "Sauce Labs Bolt T-Shirt" in the cart
    And User is on checkout information page

  @positive
  Scenario: Complete checkout successfully with valid information
    When User inputs first name "Rusdi", last name "Ngawi", and postal code "12345"
    And User clicks continue button
    Then User should be redirected to checkout overview page
    And User clicks finish button
    Then User should be redirected to checkout complete page
    And User sees order confirmation message "Thank you for your order!"

  @generate-pdf
  Scenario: Generate PDF button does not break the checkout complete page
    When User inputs first name "Rusdi", last name "Ngawi", and postal code "12345"
    And User clicks continue button
    Then User should be redirected to checkout overview page
    And User clicks finish button
    Then User should be redirected to checkout complete page
    And User clicks generate pdf order button
    Then User should still be on checkout complete page

  @back-to-products
  Scenario: Navigate back to inventory page from checkout complete page
    When User inputs first name "Rusdi", last name "Ngawi", and postal code "12345"
    And User clicks continue button
    Then User should be redirected to checkout overview page
    And User clicks finish button
    Then User should be redirected to checkout complete page
    And User clicks back to products button on checkout complete page
    Then User should be redirected to inventory page

  @order-summary
  Scenario: Order summary displays correct items and pricing for multiple products
    When User inputs first name "Rusdi", last name "Ngawi", and postal code "12345"
    And User clicks continue button
    Then User should be redirected to checkout overview page
    And The order summary should display item name "Sauce Labs Backpack" and price "$29.99"
    And The order summary should display item name "Sauce Labs Bike Light" and price "$9.99"
    And The order summary should display item name "Sauce Labs Bolt T-Shirt" and price "$15.99"
    And The order subtotal should equal the sum of item prices
    And The order total should equal subtotal plus tax

  @negative
  Scenario: Cannot continue checkout with empty first name
    When User inputs first name "", last name "Ngawi", and postal code "12345"
    And User clicks continue button
    Then User sees checkout error message "Error: First Name is required"

  @negative
  Scenario: Cannot continue checkout with empty last name
    When User inputs first name "Rusdi", last name "", and postal code "12345"
    And User clicks continue button
    Then User sees checkout error message "Error: Last Name is required"

  @negative
  Scenario: Cannot continue checkout with empty postal code
    When User inputs first name "Rusdi", last name "Ngawi", and postal code ""
    And User clicks continue button
    Then User sees checkout error message "Error: Postal Code is required"

  @cancel
  Scenario: Cancel from checkout information page returns to cart
    When User clicks cancel button on checkout information page
    Then User should be redirected to cart page

  @cancel-overview
  Scenario: Cancel from checkout overview page returns to inventory page
    When User inputs first name "Rusdi", last name "Ngawi", and postal code "12345"
    And User clicks continue button
    Then User should be redirected to checkout overview page
    And User clicks cancel button on checkout overview page
    Then User should be redirected to inventory page
