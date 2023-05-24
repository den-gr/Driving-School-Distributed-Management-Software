Feature: booking and reading info about theoretical exams
  Scenario Outline: registering exam appeal day
    Given a new day <date> and number of places <places> for exam appeal
    When secretary insert it
    Then it receives <code> with <message>

  Examples:
    | date | places |code | message |
    | 2023-10-12 | 1 |200 | OK |
    | 2023-10-12 | 2 |400 | ALREADY_DEFINED_DAY |

  Scenario: reading future exam appeal dates and info about a specific appeal
    Given list of future exam appeals
    Then secretary request info about one specific appeal in 2023-10-12
    And it receives empty list of dossiers

  Scenario: registering one subscriber in the next exam appeal
    Given exam appeal in 2023-10-12
    Then secretary registers dossier D1
    And receives code 200 with message OK
    Then secretary request info about one specific appeal in 2023-10-12
    And it receives the list of dossiers containing D1


