Feature: test feature
  @vertx
  Scenario Outline: I send request to the server
    Given I ask <num> id
    Then I receive <num> that is equal to input
    But an error

    Examples: examples
      | num |
      | 3   |
      | 1010|

  @vertx
  Scenario: So I want an error
      Given an incorrect input as aa
      Then I have an error