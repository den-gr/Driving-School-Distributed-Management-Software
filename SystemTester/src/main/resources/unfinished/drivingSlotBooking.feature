Feature: Regular driving slot booking
    Scenario Outline: instructor reads occupied driving slot for a certain date
        When i request occupied driving slots in a <date>
        Then the first driving slot is: <date>, time 9.00AM, instructor id 1, dossier id 3, vehicle FG563KT
        Then the third driving slot is: <date>, time 9.00AM, instructor id 2, dossier id 7, vehicle KF037MF
        Then the second driving slot is: <date>, time 10.00AM, instructor id 2, dossier id 5, vehicle KF037MF

    Examples:
        | date |
        | 12/04/2013 |

    Scenario Outline: instructor books a driving slot
        When i send <date>, <time>, <instructor>, <dossier>, <vehicle> to book the driving slot
        Then i receive <response>

    Examples:
        | date | time | instructor | dossier | vehicle | response |
        | 12/04/2013 | 11.00AM | 1 | 9 | KF037MF | OK |
        | 12/04/2013 | 9.00AM | 1 | 10 | GN567MG | INSTRUCTOR NOT FREE |
        | 12/04/2013 | 11.00AM | 3 | 11 | KF037MF | VEHICLE NOT FREE |
        | 12/04/2013 | 12.00AM | 3 | 12 | KF037MF | PROVISIONAL LICENSE NOT VALID |
        | 12/04/2013 | 13.00AM | 1 | 9 | GN567MG | DRIVING SLOTS ALREADY BOOKED |