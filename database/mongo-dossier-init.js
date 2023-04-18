db = db.getSiblingDB('dsdms');

db.createCollection('Dossier');

db.Dossier.insertMany( [
{
  name: "one",
  number: 44
},
{
  name: "two",
  number: 88
},
]);