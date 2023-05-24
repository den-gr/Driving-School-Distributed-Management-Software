Feature: creation and reading of theoretical exam passes
  Scenario Outline: register one valid doctor result
    When secretary register <id>, <date> and <visit_result>
    Then request finished with success, with code <code>
    And theoretical exam pass is registered

    Examples:
      | id | date | visit_result | code |
      | d1 | 2023-09-19 | VALID | 200 |

  Scenario Outline: booking two doctor visits
    When sending <id>, <date> and <visit_result> for saving doctor results
    Then request finished with not success, with code <code>
    And theoretical exam pass is not being created, receiving message <message>

    Examples:
      | id | date | visit_result | code | message |
      | d2 | 2023-09-19 | NOT_VALID | 200 | EXAM_PASS_NOT_CREATED |
      | d1 | 2023-09-20 | VALID | 400 | EXAM_PASS_ALREADY_AVAILABLE |