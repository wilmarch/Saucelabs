@Login
Feature: Login

  @positive
  Scenario: Success login with valid credentials
    Given User is on login page
    When User inputs username "standard_user" and password "secret_sauce"
    And User clicks login button
    Then User should be redirected to inventory page

  @negative
  Scenario: Login with invalid credentials
    Given User is on login page
    When User inputs username "standard_user" and password "wrong_password"
    And User clicks login button
    Then User sees error message "Epic sadface: Username and password do not match any user in this service"

  @negative
  Scenario: Login with locked out user
    Given User is on login page
    When User inputs username "locked_out_user" and password "secret_sauce"
    And User clicks login button
    Then User sees error message "Epic sadface: Sorry, this user has been locked out."

  @boundary
  Scenario: Login with empty username
    Given User is on login page
    When User inputs username "" and password "secret_sauce"
    And User clicks login button
    Then User sees error message "Epic sadface: Username is required"

  @boundary
  Scenario: Login with empty password
    Given User is on login page
    When User inputs username "standard_user" and password ""
    And User clicks login button
    Then User sees error message "Epic sadface: Password is required"