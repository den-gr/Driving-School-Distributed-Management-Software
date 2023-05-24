Feature: creation and reading of theoretical exam passes
  Scenario Outline: register one valid doctor result
    When secretary register <id>, <date>, <result>
    Then request finished with success, with code <code>
    And theoretical exam pass is registered for dossier <id>

    Examples:
      | id | date | code | result |
      | d1 | 2023-09-19 | 200 | VALID |

  Scenario Outline: booking two doctor visits
    When sending <id>, <date> and <visit_result> for saving doctor results
    Then request finished with not success, with code <doctor_code> and message <message>
    And theoretical exam pass is not being created for <id>, receiving message <exam_message> and code <exam_code>

    Examples:
      | id | date | visit_result | doctor_code | message | exam_message | exam_code |
      | d2 | 2023-09-19 | NOT_VALID | 400 | EXAM_PASS_NOT_CREATED | ID_NOT_FOUND | 404 |
      | d3 | 2023-09-20 | NEED_ONE_MORE_VISIT | 400 | EXAM_PASS_NOT_CREATED | ID_NOT_FOUND | 404 |
      | d1 | 2023-09-20 | VALID | 400 | EXAM_PASS_ALREADY_AVAILABLE | NULL | 200 |