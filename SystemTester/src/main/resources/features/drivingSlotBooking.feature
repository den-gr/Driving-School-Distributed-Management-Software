Feature: Regular driving slot booking
    Scenario Outline: instructor books a driving slot
        When i send <date>, <time>, <instructorId>, <dossierId>, <vehicle> to book the driving slot
        Then i receive <response> with <code>

        Examples:
            | date | time | instructorId | dossierId| vehicle | response | code |
            | 2023-12-04 | 09:00 | i1 | d4 | GN567MG | INSTRUCTOR_NOT_FREE | 503 |
            | 2023-12-04 | 11:00 | i1 | d3 | KF037MF | OK | 200 |
            | 2023-12-04 | 11:00 | i2 | d5 | KF037MF | VEHICLE_NOT_FREE | 503 |
#            | 2013-12-04 | 12:00AM | i2 | d1 | KF037MF | INVALID_PROVISIONAL_LICENSE | 400 |
            | 2023-12-04 | 13:00 | i1 | d1 | GN567MG | OCCUPIED_DRIVING_SLOTS | 503 |
            | 2023-12-18 | 13:00 | i1 | d6 | GN567MH | BAD_VEHICLE_INSTRUCTOR_INFO | 404 |
            | 2023-12-18 | 13:00 | i3 | d6 | GN567MH | BAD_VEHICLE_INSTRUCTOR_INFO | 404 |

    Scenario Outline: instructor reads occupied driving slot for a certain date
        When i request occupied driving slots in a <date>
        Then the first driving slot is: <date>, time 09:00, instructor id i1, dossier id d2, vehicle HG745BC
        Then the second driving slot is: <date>, time 10:00, instructor id i2, dossier id d1, vehicle HG745BC
        Then the third driving slot is: <date>, time 10:30, instructor id i1, dossier id d1, vehicle FZ340AR

    Examples:
        | date |
        | 2023-12-04 |