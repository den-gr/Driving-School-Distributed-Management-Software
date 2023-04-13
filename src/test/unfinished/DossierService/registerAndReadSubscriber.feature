Feature: registration and reading of new dossier
    @vertx
    Scenario Outline: subscriber informations are correct
        When I send <name>, <surname>, <cf> to server
        Then I received Id of registered dossier, Ok
        And when i try to read information with Id
        Then I receive <name>, <surname>, <cf>

    Examples: basic information
    | name | surname | cf | theoretical exam status |
    | Riccardo | Bacca | BCCRCR99C07C573X | PASSED |

    @vertx
    Scenario Outline: subscriber informations are not correct
        When I send <name>, <surname>, <cf> to server
        Then I received Bad information error message

    Examples: basic information
    | name | surname | cf |
    | b | 123 | BCCRCR99C07C573X |

    @vertx
    Scenario Outline: subscriber informations are alreay available in a valid dossier
        When I send <name>, <surname>, <cf> to server
        Then I received Duplicated error message

    Examples: basic information
    | name | surname | cf |
    | Riccardo | Bacca | BCCRCR99C07C573X |
