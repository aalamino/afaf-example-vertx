Feature: Joker
  As a jokes lover I would like to get some fun jokes

  @CleanScenario
  Scenario: 
  Given a server endpoint "/joker"
  When I send a GET request to the endpoint
  Then I get an HTTP "200"
  And contains field "joke" not empty