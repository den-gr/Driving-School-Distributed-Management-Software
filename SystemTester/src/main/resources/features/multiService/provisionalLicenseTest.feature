Feature: creation of provisional license
  Scenario: provisional license creation after successful theoretical exam
    Given a new exam appeal on 2023-11-14
    When the dossier is registered to exam appeal
    Then the subscriber passes the theoretical exam so secretary creates new provisional license
    And if try to register an another provisional license for this dossier receive PROVISIONAL_LICENSE_ALREADY_EXISTS msg

  Scenario: reading information about previously created provisional license
    When requesting info about registered provisional license
    Then receiving info that there are 0 failing attempts and validity range is from 2023-11-14 to 2024-11-14

  Scenario: reading information about exam status for the created dossier
    When secretary requests info about his exam status
    Then it receives exam status object, with theoretical exam status true
