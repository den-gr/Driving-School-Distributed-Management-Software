db = db.getSiblingDB('dossier_service');
db.createCollection('Dossier');
db.Dossier.insertMany([
    {
      _id: "d1",
      name: "prova1",
      surname: "prova1",
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
db2.createCollection('Vehicles');
db2.DrivingSlot.insertMany([
    {
      _id: "b1",
      date:"2013-12-04",
      time: "10:30",
      instructorId: "i1",
      dossierId: "d1",
      vehicle: "FZ340AR"
    },
    {
      _id: "b2",
      date:"2013-12-04",
      time: "09:00",
      instructorId: "i2",
      dossierId: "d2",
      vehicle: "HG745BC"
    },
    {
      _id: "b3",
      date:"2013-12-04",
      time: "10:00",
      instructorId: "i2",
      dossierId: "d1",
      vehicle: "HG745BC"
    }
]);
db2.Instructor.insertMany([
    {
      _id: "i1",
      name: "riccardo",
      surname: "bacca"
    },
    {
      _id: "i2",
      name: "denys",
      surname: "grushchak"
    }
]);
db2.Vehicles.insertMany([
    {
      licensePlate: "FZ340AR",
      model: "Jeep Renegade"
    },
    {
      licensePlate: "HG745BC",
      model: "Fiat Punto"
    }
]);