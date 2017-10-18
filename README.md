# tikape-runko

Tietokantojen perusteet -kurssilla tehtävän web-sovelluksen pohja.

Tietokannan Aseluvat.db schematiikka:

  CREATE TABLE Varusmies(
  hetu varchar(10) PRIMARY KEY,
  nimi varchar(50));

  CREATE TABLE Ase(
  aseenNumero integer PRIMARY KEY,
  Asetyyppi varchar(20));

  CREATE TABLE Kayttooikeus(
  varusmies_hetu varchar(10),
  ase_aseenNumero integer,
  FOREIGN KEY (varusmies_hetu) REFERENCES Varusmies(hetu),
  FOREIGN KEY (ase_aseenNumero) REFERENCES Ase(aseenNumero));

Taulujen sisältö:
  Varusmies:
  
    INSERT INTO Varusmies(hetu, nimi) VALUES (
    '060606A666R',
    'Lucia Beelzebub');

    INSERT INTO Varusmies(hetu, nimi) VALUES (
    '070707A7771',
    'Hessus Nassez');
  Ase:
  
    INSERT INTO Ase(aseenNumero, asetyyppi) VALUES (
    615667,
    'RK-62');
  Kayttooikeus
  
    INSERT INTO Kayttooikeus(varusmies_hetu, ase_aseenNumero) VALUES (
    '060606A666R',
    615667);

    INSERT INTO Kayttooikeus(varusmies_hetu, ase_aseenNumero) VALUES (
    '070707A7771',
    615667);
