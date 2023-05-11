Feature: Update subscriber informations
  Scenario Outline: updating examStatus
    Given a new registered dossier: <name>, <surname>, <birthdate>, <fc>
    Then i request the dossier from server with obtained id
    When i read his <type> exam status is false
    Then trying to update <type> exam status to true
    And obtaining <type> exam status true as response from server

  Examples:
    | type | name | surname | birthdate | fc |
    | theoretical | Mario | Rossi | 1999-03-07 | 123CDF |
    | practical | Luca | Bianchi | 1999-03-07 | 456EFG |