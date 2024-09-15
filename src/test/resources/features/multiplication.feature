Feature: Multiplication

  @regression @multiplication
  Scenario: Multiplication
    Given I have the first number 4
    And I have the second number 3
    When I multiply the numbers
    Then the result should be 12