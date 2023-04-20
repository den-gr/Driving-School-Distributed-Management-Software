Feature: Update subscriber informations
  @vertx
  Scenario Outline: updating examStatus
    Given a new registered dossier: <name>, <surname>, <fc>
    Then i request the dossier from server with obtained id
    When i read his <type> exam status is false
    Then trying to update <type> exam status to true
    And obtaining <type> exam status true as response from server

  Examples:
    | type | name | surname | fc |
    | theoretical | Mario | Rossi | 123CDF |
    | practical | Luca | Bianchi | 456EFG |