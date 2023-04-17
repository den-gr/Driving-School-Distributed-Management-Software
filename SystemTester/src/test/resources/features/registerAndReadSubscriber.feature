Feature: registration and reading of new dossier
    @vertx
    Scenario Outline: subscriber informations are correct
        When I send <name>, <surname>, <fiscal_code> to server
        Then I received Id of registered dossier

    Examples: basic information
    | name | surname | fiscal_code |
    | Riccardo | Bacca | BCCRCR99C07C573X |