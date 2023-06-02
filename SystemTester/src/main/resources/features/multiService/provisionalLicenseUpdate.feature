Feature: Register practical exam failure and success
  Scenario: subscriber passed the practical exam and this should be registered
    Given dossier d99 that is associated with a valid provisional license
    When register PASSED state for practical exam
    Then provisional license is deleted so ID_NOT_FOUND
    And dossier practical exam status is PASSED

  Scenario: subscriber that use the second provisional license fails exams again and get invalidation of its dossier
    Given dossier d2 already has FIRST_PROVISIONAL_LICENCE_INVALID and 1 failed attempts of practical exams
    When register a practical exam fail, the number of failed attempts become 2
    And if do it again
    Then provisional license is deleted so get ID_NOT_FOUND message
    And dossier is invalid and has practical exam state SECOND_PROVISIONAL_LICENCE_INVALID