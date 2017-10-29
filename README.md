# tikape-runko
Tekijät:

David Forsman, 014245068

Oula Magga, 014021224

Anton Rantamaa, 014471146

Jessica Koski, 01489371

Tietokantojen perusteet -kurssilla tehtavan web-sovelluksen pohja:

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

Taulujen sisalto:
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

Web-sovelluksessa käytetyt osoitteet:

    näytä pääsivu: get->/paasivu (paasivu)
    näytä aseet: get->/aseet (aseet)
    näytä varusmiehet: get->/varusmiehet (varusmiehet)
    näytä haku: get->/haku, suorita haku: post->/haku (etsinta)
    näytä aseen lisäys: get->/aselisays, suorita aseen lisäys: post->/aselisays (lisaaase)
    näytä varusmiehen lisäys: get->/varusmieslisays (lisaavarusmies)
    suorita varusmiehen lisäys: post->/varusmieslisays
    näytä yksittäinen ase: get->/ase/:id, missä :id on aseen numero (ase)
    poista yksittäinen ase tietokannasta: post->/ase/poista/:id
    lisää aseelle käyttöoikeus: post->/ase/:id
    näytä yksittäinen varusmies: get->/varusmies/:id, missä :id on varusmiehen hetu (varusmies)
    näytä varusmies tai ase: get-> /:id, missä id voi olla varusmiehen hetu tai aseen numero    
    poista yksittäinen varusmies tietokannasta: post->/varusmies/poista/:id
    lisää varusmiehelle käyttöoikeus: post->/kayttooikeus
    poista varusmieheltä käyttöoikeus: get->/kayttooikeus/:id, missä id on kayttooikeuden id

osoitteet lisätty seuraaviin:

    ase.html
    aseenHaku.html
    aseet.html
    lisaaase.html
    lisaavarusmies.html
    paasivu.html
    VarusmiehenHaku.html

    Suosittelemme aloittamaan sivusta 
    http://localhost:4567/paasivu
