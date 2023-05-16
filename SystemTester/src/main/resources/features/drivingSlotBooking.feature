Feature: Regular driving slot booking
    Scenario Outline: instructor books a driving slot
        When i send <date>, <time>, <instructorId>, <dossierId>, <vehicle> to book the driving slot
        Then i receive <response> with <code>

        # ProvisionalLicense:
        # for d1, d2, d3, d4, d5 expiry date is 2024-01-23
        # d6's provisional license expires 2023-12-20
        # d7 does not have a provisional license
        Examples:
            | date | time | instructorId | dossierId| vehicle | response | code |
            | 2023-12-04 | 09:00 | i1 | d4 | GN567MG | INSTRUCTOR_NOT_FREE | 400 |
            | 2023-12-04 | 11:00 | i1 | d3 | KF037MF | OK | 200 |
            | 2023-12-04 | 11:00 | i2 | d5 | KF037MF | VEHICLE_NOT_FREE | 400 |
            | 2023-12-04 | 13:00 | i1 | d1 | GN567MG | OCCUPIED_DRIVING_SLOTS | 400 |
            | 2023-12-04 | 13:00 | i1 | d6 | GN567MH | BAD_VEHICLE_INSTRUCTOR_INFO | 404 |
            | 2023-12-04 | 13:00 | i3 | d7 | GN567MH | NO_PROVISIONAL_LICENSE | 400 |
            | 2023-12-23 | 13:00 | i3 | d6 | GN567MH | INVALID_PROVISIONAL_LICENSE | 400 |

    Scenario Outline: instructor reads occupied driving slot for a certain date
        When i request occupied driving slots in a <date>
        Then the first driving slot is: <date>, time 09:00, instructor id i1, dossier id d2, vehicle HG745BC
        Then the second driving slot is: <date>, time 10:00, instructor id i2, dossier id d1, vehicle HG745BC
        Then the third driving slot is: <date>, time 10:30, instructor id i1, dossier id d1, vehicle FZ340AR

        Examples:
            | date |
            | 2023-12-04 |

    Scenario: instructor wants to delete a bad driving slot previously added
        When i send 2023-11-05, 09:00, i1, d4, FZ340AR to book the bad driving slot
        Given the id of the inserted driving slot
        When attempting to remove it, i receive code 200
        Then when attempting to remove it another time (wrongly), i receive code 400 with DELETE_ERROR