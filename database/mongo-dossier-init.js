dossierDB = db.getSiblingDB('dossier_service');
dossierDB.createCollection('Dossier');
dossierDB.Dossier.insertMany([
    {
      _id: "d1",
      name: "prova1",
      surname: "prova1",
      birthdate: "1999-03-07",
      fiscal_code: "MR45G3",
      validity: true,
      examsStatus: {theoreticalExamState: "TO_DO", practicalExamState: "TO_DO"}
    },
    {
      _id: "d2",
      name: "prova2",
      surname: "prova2",
      birthdate: "1999-03-07",
      fiscal_code: "MFH7594",
      validity: true,
      examsStatus: {theoreticalExamState: "PASSED", practicalExamState: "FIRST_PROVISIONAL_LICENCE_INVALID"}
    },
    {
      _id: "d99",
      name: "prova3",
      surname: "prova3",
      birthdate: "1989-03-07",
      fiscal_code: "MFH7594KK",
      validity: true,
      examsStatus: {theoreticalExamState: "TO_DO", practicalExamState: "TO_DO"}
    }
]);

drivingDB = db.getSiblingDB('driving_service');
drivingDB.createCollection('DrivingSlot');
drivingDB.createCollection('Instructor');
drivingDB.createCollection('Vehicle');
drivingDB.DrivingSlot.insertMany([

    {
      _id: "b2",
      date:"2023-12-04",
      time: "09:00",
      instructorId: "i1",
      dossierId: "d2",
      licensePlate: {
        numberPlate: "HG745BC"
      },
      slotType: "ORDINARY"
    },
    {
      _id: "b3",
      date:"2023-12-04",
      time: "10:00",
      instructorId: "i2",
      dossierId: "d1",
      licensePlate: {
        numberPlate: "HG745BC"
      },
      slotType: "ORDINARY"
    },
    {
      _id: "b1",
      date:"2023-12-04",
      time: "10:30",
      instructorId: "i1",
      dossierId: "d1",
      licensePlate: {
        numberPlate: "FZ340AR"
      },
      slotType: "ORDINARY"
    }
]);
drivingDB.Instructor.insertMany([
    {
      _id: "i1",
      name: "riccardo",
      surname: "bacca",
      fiscal_code: "BCCRCR99C07C573X"
    },
    {
      _id: "i2",
      name: "denys",
      surname: "grushchak",
      fiscal_code: "DRGOENMD75493MV"
    }
]);
drivingDB.Vehicle.insertMany([
    {
      licensePlate: {
        numberPlate: "FZ340AR"
      },
      manufacturer: "Jeep",
      model: "Renegade",
      year: 2020
    },
    {
      licensePlate: {
        numberPlate: "HG745BC"
      },
      manufacturer: "Fiat",
      model: "Punto",
      year: 2016
    },
    {
      licensePlate: {
        numberPlate: "GN567MG"
      },
      manufacturer: "Audi",
      model: "A3",
      year: 2015
    },
    {
      licensePlate: {
        numberPlate: "KF037MF"
      },
      manufacturer: "BMW",
      model: "118d",
      year: 2014
    }
]);

examDB = db.getSiblingDB('exam_service');
examDB.createCollection('ProvisionalLicenseHolders');
examDB.ProvisionalLicenseHolders.insertMany([
      {
        "provisionalLicense": {
          "dossierId": "d1",
          "startValidity": new Date("2023-01-25"),
          "endValidity": new Date("2024-01-25")
        },
        "practicalExamAttempts": 0
      },
      {
        "provisionalLicense": {
          "dossierId": "d2",
          "startValidity": new Date("2023-01-25"),
          "endValidity": new Date("2024-01-25")
        },
        "practicalExamAttempts": 1
      },
      {
        "provisionalLicense": {
          "dossierId": "d3",
          "startValidity": new Date("2023-01-25"),
          "endValidity": new Date("2024-01-25")
        },
        "practicalExamAttempts": 0
      },
      {
        "provisionalLicense": {
          "dossierId": "d4",
          "startValidity": new Date("2023-01-25"),
          "endValidity": new Date("2024-01-25")
        },
        "practicalExamAttempts": 0
      },
      {
        "provisionalLicense": {
          "dossierId": "d5",
          "startValidity": new Date("2022-11-25"),
          "endValidity": new Date("2023-11-25")
        },
        "practicalExamAttempts": 0
      }
]);