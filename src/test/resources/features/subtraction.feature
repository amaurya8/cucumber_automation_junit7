Feature: Subtraction

  @regression
  Scenario: Subtraction
    Given I have the first number 10
    And I have the second number 5
    When I subtract the numbers
    Then the result should be 5