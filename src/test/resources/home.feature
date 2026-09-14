@HomePage
Feature: Homepage

  Background:
    Given User is on login page
    When User inputs username "standard_user" and password "secret_sauce"
    And User clicks login button
    Then User should be redirected to inventory page

  @sorting
  Scenario Outline: Verify product sorting functionality
    When User selects filter option "<filter_option>"
    Then The first product item name should be "<expected_name>"

    Examples:
      | filter_option       | expected_name                     |
      | Name (A to Z)       | Sauce Labs Backpack               |
      | Name (Z to A)       | Test.allTheThings() T-Shirt (Red) |
      | Price (low to high) | Sauce Labs Onesie                 |
      | Price (high to low) | Sauce Labs Fleece Jacket          |

  @logout
  Scenario: User log out successfully from homepage
    When User clicks burger menu button
    And User clicks logout sidebar link
    Then User should be redirected to login page

  @about
  Scenario: Verify About link points to correct external URL
    Given User is on login page
    When User inputs username "standard_user" and password "secret_sauce"
    And User clicks login button
    And User clicks burger menu button
    Then The About link should point to "https://saucelabs.com/"

  @cart-add
  Scenario: User adds a product to the cart
    When User adds the following products to cart
      | Sauce Labs Backpack |
    Then The shopping cart badge should display "1"

  @cart-remove
  Scenario: User removes a product from the cart
    Given User has already added the following products to the cart
      | Sauce Labs Backpack |
    When User removes the following products from cart
      | Sauce Labs Backpack |
    Then The shopping cart badge should disappear

  @product-detail
  Scenario Outline: Verify product details match the homepage selection
    When User clicks on product title "<product_name>"
    Then User should be redirected to product detail page
    And The product details should display name "<product_name>" and price "<expected_price>"

    Examples:
      | product_name              | expected_price |
      | Sauce Labs Backpack       | $29.99         |
      | Sauce Labs Bike Light     | $9.99          |
      | Sauce Labs Bolt T-Shirt   | $15.99         |