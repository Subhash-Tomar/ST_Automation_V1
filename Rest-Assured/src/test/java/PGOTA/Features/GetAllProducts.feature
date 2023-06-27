Feature: Validate Products
  Validate responsetime and product details

  Scenario: Validate products
    Given API is available
    When call GET method
    Then Response should be "ok"
    And Resposnse time Should be less than "1500"ms
