Feature: Registration of practical exams
  Scenario: Try to book a practical exam without having 10 completed driving lessons
    Given an attempt to book a new practical exam on 2023-10-12 for instructor i1, dossier d5 and auto KF037MF
    Then receive the NOT_ENOUGH_DRIVING_LESSONS_FOR_EXAM response

  Scenario: Try to book a practical exam in not the practical exam day
    Given dossier d5 has already 10 driving lessons in the past
    When an attempt to book a new practical exam on 2023-10-12 for instructor i1, dossier d5 and auto KF037MF
    Then receive the NOT_AN_EXAM_DAY response for this day

  Scenario Outline: Registering an practical exam day
    When registering a new practical exam day on <date>
    Then it receives <code> with <message>

    Examples:
      | date | code | message |
      | 2023-10-12 | 200 | OK |
      | 2023-10-12 | 400 | ALREADY_DEFINED_AS_PRACTICAL_EXAM_DAY |

  Scenario: Reading the list of practical exam days
    When request a list of practical exam days
    Then finds only one available practical exam day on 2023-10-12

  Scenario: Finally the correct booking of practical exam
    Given an attempt to book a new practical exam on 2023-10-12 for instructor i1, dossier d5 and auto KF037MF
    Then receive confirmation of successful booking

