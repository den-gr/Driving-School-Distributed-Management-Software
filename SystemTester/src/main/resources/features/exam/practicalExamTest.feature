Feature: booking and reading info about practical exams
  Scenario Outline: registering exam appeal day
    Given a new day <date> and number of places <places> for exam appeal
    When secretary insert it
    Then it receives <code> with <message>

    Examples:
      | date | places |code | message |
      | 2023-10-12 | 1 |200 | OK |
      | 2023-10-12 | 2 |400 | ALREADY_DEFINED_DAY |

