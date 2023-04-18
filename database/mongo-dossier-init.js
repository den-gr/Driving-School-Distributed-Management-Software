db = db.getSiblingDB('dsdms');

db.createCollection('dossier');

db.dossier.insertMany( [
{
  name: "one",
  number: 44
},
{
  name: "two",
  number: 88
},
]);