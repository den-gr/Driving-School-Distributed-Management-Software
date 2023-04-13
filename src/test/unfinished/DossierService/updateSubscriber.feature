Feature: Update subscriber informations
    @vertx
    Scenario Outline: updating theoretical exam status
        Given a new registered dossier
        When i read his theoretical exam status
        Then his theoretical exam status is <unpassed>
        And when trying to update theoretical exam status = <passed>
        Then I receive <passed>

    Examples: basic information
    | passed | unpassed |
    | PASSED | UNPASSED |

    @vertx
    Scenario Outline: updating practical exam status
        Given a new registered dossier
        When i read his theoretical exam status
        Then his theoretical exam status is <unpassed>
        And when trying to update theoretical exam status = <passed>
        Then I receive <passed>

    Examples: basic information
    | passed | unpassed |
    | PASSED | UNPASSED |

    @vertx
    Scenario Outline: reading dossier status
        Given a new registered dossier
        When i read his dossier status
        And his dossier status is inactive
        Then I receive PASSED

        
