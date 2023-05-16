Feature: registration and reading of new dossier
    Scenario Outline: subscriber information's are correct
        When I send <name>, <surname>,<birthdate>,<fiscal_code> to server
        Then I received id of registered dossier
        When I send id to server
        Then I received <name>,<surname>,<birthdate>,<fiscal_code> of registered dossier

    Examples: basic information
        | name | surname | birthdate | fiscal_code |
        | Riccardo | Bacca | 1999-03-07 | BCCRCR99C07C573X |

    Scenario Outline: The subscriber documents information has invalid values
        When I try to register invalid subscriber information: <name>,<surname>,<birthdate>,<fiscal_code>
        Then I get <error_type> error type

    Examples: invalid subscriber information
        | name | surname | birthdate | fiscal_code | error_type |
        | Riccardo | Bacca | 1999-03-07 | BCCRCR99C07C573X | VALID_DOSSIER_ALREADY_EXISTS |
        | b        | 123   | 1999-03-07 | BCCRCR99C07C573L | NAME_SURNAME_NOT_STRING      |
