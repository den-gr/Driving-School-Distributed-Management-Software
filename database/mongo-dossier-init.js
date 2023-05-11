db = db.getSiblingDB('dossier_service');
db.createCollection('Dossier');
db.Dossier.insertMany([
    {
      _id: "d1",
      name: "prova1",
      surname: "prova1",
      birthdate: "1999-03-07",
      fiscal_code: "MR45G3",
      validity: true,
      examAttempts: {
        attempts: 0
      },
      examStatus: {
        practical: false,
        theoretical: false
      }
    },
    {
      _id: "d2",
      name: "prova2",
      surname: "prova2",
      birthdate: "1999-03-07",
      fiscal_code: "MFH7594",
      validity: true,
      examAttempts: {
        attempts: 0
      },
      examStatus: {
        practical: false,
        theoretical: false
      }
    }
]);

db2 = db.getSiblingDB('driving_service');
db2.createCollection('DrivingSlot');
db2.createCollection('Instructor');
db2.createCollection('Vehicle');
db2.DrivingSlot.insertMany([

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
db2.Instructor.insertMany([
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
db2.Vehicle.insertMany([
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