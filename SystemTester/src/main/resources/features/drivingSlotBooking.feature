Feature: Regular driving slot booking
    Scenario Outline: instructor reads occupied driving slot for a certain date
        When i request occupied driving slots in a <date>
        Then the first driving slot is: <date>, time 10:30, instructor id i1, dossier id d1, vehicle FZ340AR
        Then the second driving slot is: <date>, time 9.00, instructor id i2, dossier id d2, vehicle HG745BC
        Then the third driving slot is: <date>, time 10.00, instructor id i2, dossier id d1, vehicle HG745BC

    Examples:
        | date |
        | 2013-12-04 |