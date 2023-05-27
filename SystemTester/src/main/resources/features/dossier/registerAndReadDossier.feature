Feature: Registration subscriber documents and reading dossier information
    Scenario Outline: subscriber information's are correct
        When I register subscriber's documents information: <name>, <surname>,<birthdate>,<fiscal_code>
        Then I received an id of registered dossier
        When I search dossier by received id
        Then I find <name>,<surname>,<birthdate>,<fiscal_code> of registered dossier
        And It has not done both practical and theoretical exams

    Examples: basic information
        | name | surname | birthdate | fiscal_code |
        | Riccardo | Bacca | 1999-03-07 | BCCRCR99C07C573X |

    Scenario Outline: The subscriber documents information has invalid values
        When I try to register invalid subscriber information: <name>,<surname>,<birthdate>,<fiscal_code>
        Then I get <error_type> error type

    Examples: invalid subscriber information
        | name | surname | birthdate | fiscal_code | error_type |
        | Riccardo | Bacca | 1999-03-07 | BCCRCR99C07C573X | VALID_DOSSIER_ALREADY_EXISTS |
        | Ricca    | Bacca | 2015-03-07 | BCCRCR99C07C573K | AGE_NOT_SUFFICIENT           |
        | b        | 123   | 1999-03-07 | BCCRCR99C07C573L | NAME_SURNAME_NOT_STRING      |
