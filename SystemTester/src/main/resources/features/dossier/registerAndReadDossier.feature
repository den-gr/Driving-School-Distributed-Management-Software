Feature: registration and reading of new dossier
    Scenario Outline: subscriber information's are correct
        When I send <name>, <surname>,<birthdate>,<fiscal_code> to server
        Then I received id of registered dossier
        When I send id to server
        Then I received <name>,<surname>,<birthdate>,<fiscal_code> of registered dossier

    Examples: basic information
        | name | surname | birthdate | fiscal_code |
        | Riccardo | Bacca | 1999-03-07 | BCCRCR99C07C573X |

    Scenario: subscriber information's are not correct
        When I send bad informations b, 123, 1999-03-07, BCCRCR99C07C573X to server
        Then I received Bad request error message


    Scenario Outline: subscriber informations are already available in a valid dossier
        When I send duplicated informations <name>,<surname>,<birthdate>,<fiscal_code> to server
        Then I received bad request error message

    Examples: basic information
        | name | surname | birthdate | fiscal_code |
        | Riccardo | Bacca | 1999-03-07 | BCCRCR99C07C573X |