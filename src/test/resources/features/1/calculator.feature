Feature: Calculator

  @regression
  Scenario: Add two numbers
    Given I have the first number 5
    And I have the second number 10
    When I add the numbers
    Then the result should be 15