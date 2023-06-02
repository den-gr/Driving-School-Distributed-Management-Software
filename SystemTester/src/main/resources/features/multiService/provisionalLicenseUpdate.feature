Feature: Register practical exam failure and success
  Scenario: subscriber passed the practical exam and this should be registered
    Given dossier d99 that is associated with a valid provisional license
    When register PASSED state for practical exam
    Then provisional license is deleted so ID_NOT_FOUND
    And dossier practical exam status is PASSED