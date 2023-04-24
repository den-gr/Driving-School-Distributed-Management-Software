Feature: Update subscriber informations
    @vertx
    Scenario Outline: updating <Type>
        Given a new registered dossier
        When i read his <Type>
        Then his <Type> is <Status before>
        And when trying to update <Type> <Status after>
        Then it become <Status after>

    Examples: examples
        | Type | Status before | Status after |
        | Theoretical_exam_status | UNPASSED | PASSED |
        | Practical_exam_status | UNPASSED | PASSED |

    @vertx
    Scenario: reading inactive dossier and attempting to modify its Practical_exam_status
        Given Id of a registered dossier
        When i read his dossier status
        And his dossier status is INACTIVE
        When attempting to modify Practical_exam_status to ACTIVE
        Then i receive Bad request error message