@checkout
Feature: Checkout Flow

  Background:
    Given User is logged in as "standard_user"
    And User has "Sauce Labs Backpack" in the cart
    And User is on checkout information page

  @positive
  Scenario: Complete checkout successfully with valid information
    When User inputs first name "Rusdi", last name "Ngawi", and postal code "12345"
    And User clicks continue button
    Then User should be redirected to checkout overview page
    And User clicks finish button
    Then User should be redirected to checkout complete page
    And User sees order confirmation message "Thank you for your order!"

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
