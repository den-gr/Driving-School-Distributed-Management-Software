Feature: Registration of practical exams
  Scenario Outline: Registering an exam appeal day
    When registering a new exam appeal day on <date> with <places> places
    Then it receives <code> with <message>

    Examples:
      | date | places |code | message |
      | 2023-10-12 | 1 |200 | OK |
      | 2023-10-12 | 2 |400 | ALREADY_DEFINED_DAY |

  Scenario: Reading the list of practical exam appeal days
    When request a list of appeals
    Then finds only one available appeal on 2023-10-12

