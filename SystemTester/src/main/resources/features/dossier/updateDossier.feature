Feature: Update subscriber informations
  Scenario Outline: updating examStatus
    Given an already registered dossier with id d1
    When i read his <type> exam progress state is <exam_state_before>
    Then trying to register <type> exam state as passed
    And obtaining <type> exam state equal to <exam_state_after>

  Examples:
    | type | exam_state_before | exam_state_after |
    | THEORETICAL_EXAM_PASSED  | TO_DO   | PASSED |
    | PRACTICAL_EXAM_PASSED    | TO_DO   | PASSED |