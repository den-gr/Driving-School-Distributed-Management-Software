db = db.getSiblingDB('dossier_service');

db.createCollection('Dossier');

db.Dossier.insertMany([
    {
      _id: {
        $oid: "644113e59003b0695a6a1152"
      },
      name: "mario",
      surname: "rossi",
      fiscal_code: "MR45G3",
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