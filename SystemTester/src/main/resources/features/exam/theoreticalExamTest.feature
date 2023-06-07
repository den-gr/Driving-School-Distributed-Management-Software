Feature: booking and reading info about theoretical exams
  Scenario Outline: registering exam appeal day
    When secretary inserts a new day <date> and number of places <places> for exam appeal
    Then it receives a result with code <code> and message <message>

  Examples:
    | date | places |code | message |
    | 2023-10-12 | 2 | 200 | OK |
    | 2023-10-12 | 2 | 400 | DATE_ALREADY_IN |

  Scenario: reading future exam appeal dates and info about a specific appeal
    Given list of future exam appeals
    Then secretary request info about one specific appeal in 2023-10-12
    And it receives empty list of dossier ids

  Scenario Outline: registering one subscriber in the next exam appeal
    When secretary registers dossier <dossierId> in exam appeal in date <date>
    Then receives code <code> with message <message>

    Examples:
      | date | dossierId | code | message |
      | 2023-10-12 | D1  | 200  | OK      |
      | 2023-10-12 | D2 | 200 | OK |
      | 2023-10-13 | D1  | 404  | APPEAL_NOT_FOUND |
      | 2023-10-12 | D1 | 400 | DOSSIER_ALREADY_PUT |
      | 2023-10-12 | D3 | 400 | PLACES_FINISHED |