Feature: creation of provisional license
  Scenario: provisional license creation after successful theoretical exam
    Given a new registered dossier
    And a new exam appeal on 2023-10-12
    When the dossier is registered to exam appeal
    Then the subscriber passes the theoretical exam so secretary creates new provisional license

  Scenario: reading information about previously created provisional license
    Given Id of the provisional license
    When requesting info about it
    Then receiving initial date 2023-10-12 and finish date 2024-10-12

  Scenario: reading information about exam status for the created dossier
    Given the Id of the previously created dossier
    When secretary requests info about his exam status
    Then it receives exam status object, with theoretical exam status true
