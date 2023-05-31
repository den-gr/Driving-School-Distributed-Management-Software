Feature: Reading and writing doctor slots
  Scenario Outline: secretary wants to book two doctor visits
    When sending <id> for registering doctor visit on <date> and <time>
    Then secretary receives <message> with <code>

    Examples:
    | id | date | time | code | message |
    | d1 | 2023-09-19 | 18:15 | 200 | 2023-09-19 |
    | d2 | 2023-09-19 | 18:15 | 400 | TIME_OCCUPIED |
    | d1 | 2023-09-22 | 19:00 | 400 | DOSSIER_ALREADY_BOOKED |
    | d2 | 2023-09-22 | 20:00 | 400 | BAD_TIME |
    | d2 | 2023-09-24 | 18:30 | 400 | NOT_DOCTOR_DAY |
    | d3 | 2023-09-22 | 18:30 | 400 | DOSSIER_NOT_EXIST |

  Scenario: secretary wants to see booked doctor slots
    When it sends 2023-09-19
    Then receives list of doctor slots, containing one doctor slot for dossier d1 at 18:15