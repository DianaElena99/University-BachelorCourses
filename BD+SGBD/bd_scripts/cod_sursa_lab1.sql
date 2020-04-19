CREATE DATABASE ProiectBD;

CREATE TABLE Adresa
(
	AdrID INT NOT NULL UNIQUE IDENTITY,
	Country VARCHAR(50) NOT NULL,
	Region VARCHAR(50) NOT NULL,
	City VARCHAR(50) NOT NULL,
	Street VARCHAR(50) NOT NULL,
	ZIP_Code VARCHAR(10) NOT NULL
);

CREATE TABLE ProgrameDeStudiu(
	IDB INT PRIMARY KEY,
	TipProgram VARCHAR(30) NOT NULL, /*Licenta, Master, Doctorat, PostUniversitar*/
	Field VARCHAR(50) NOT NULL,
	UniID INT NOT NULL FOREIGN KEY
	REFERENCES Universitate ( UnivID ),
	StudyYears INT NOT NULL,
	NumberOfStudents INT NOT NULL,
);

CREATE TABLE Universitate
(
	UnivID INT NOT NULL PRIMARY KEY,
	Nume VARCHAR(50) NOT NULL,
	Rating FLOAT,
	Adresa INT NOT NULL FOREIGN KEY 
	REFERENCES Adresa(AdrID),
);


CREATE TABLE Domenii
(
	NumeDomeniu VARCHAR(50) PRIMARY KEY,
);


CREATE TABLE Profesori
(
	CodProf INT PRIMARY KEY,
	Field VARCHAR(50) NOT NULL FOREIGN KEY
	REFERENCES Domenii(NumeDomeniu),
	UniID INT NOT NULL FOREIGN KEY
	REFERENCES Universitate (UnivID),
	Grad VARCHAR(20) NOT NULL,
	NrCoursesPerSemester INT NOT NULL,
);

CREATE TABLE Studenti
(
	CodStud INT PRIMARY KEY ,
	UniID INT NOT NULL FOREIGN KEY
	REFERENCES Universitate(UnivID) ,
	Nume VARCHAR(50) NOT NULL,
	Prenume VARCHAR(50) NOT NULL,
	Email VARCHAR(100) NOT NULL,
	Phone INT NOT NULL,
	ProgramAttending INT NOT NULL FOREIGN KEY
	REFERENCES ProgrameDeStudiu(IDB),
	NumberOfCreditsLastSemester INT,
	Bursa INT, /*0 daca n-are bursa, alt nr = cata bursa are*/
	Buget INT, /*0 daca e la taxa, 1 daca e la buget*/ 
);


CREATE TABLE BurseERASMUS
(
	Country VARCHAR(50) NOT NULL,
	Univ INT NOT NULL FOREIGN KEY
	REFERENCES Universitate(UnivID),
	Quantum INT NOT NULL,
	YearOfStudy INT,
	AvailablePlaces INT,
	Field VARCHAR(50) NOT NULL,
	ApplicationDeadline DATETIME, 
	CONSTRAINT PK_Erasmus PRIMARY KEY
	(Country, Univ, Field),
);

CREATE TABLE Decanat(
	Univ INT NOT NULL FOREIGN KEY
	REFERENCES Universiate(UnivID),
	ID_Decan INT NOT NULL FOREIGN KEY
	REFERENCES Profesori (CodProf),
);

CREATE TABLE MedieMaterie(
	Nota INT NOT NULL,
	Student INT NOT NULL FOREIGN KEY
	REFERENCES Studenti(CodStud),
	Materie INT NOT NULL FOREIGN KEY
	REFERENCES Materii(CodMaterie),
	Promovat INT NOT NULL , /*0 - RESTANTA , 1- PROMOVAT*/
);

CREATE TABLE MediiGenerale(
	MediaSem1 INT NOT NULL,
	MediaSem2 INT NOT NULL, 
	MediaGenerala INT NOT NULL,
	Student INT NOT NULL FOREIGN KEY 
	REFERENCES Studenti(CodStud),
	CONSTRAINT ID_medie PRIMARY KEY
	(MediaGenerala, Student),
);

CREATE TABLE Materii
(
	CodMaterie INT NOT NULL PRIMARY KEY,
	NumeMaterie VARCHAR(50) NOT NULL, 
	Domeniu VARCHAR(50) NOT NULL,
	ProgramDeStudiu INT NOT NULL ,
);

/*
Many to Many relationship 
O materie poate fi predata la mai multe universitati
O universitate preda mai multe materii
*/
CREATE TABLE MaterieUniversitate
(
	CodMaterie INT NOT NULL FOREIGN KEY
	REFERENCES Materii(CodMaterie),
	CodUniversitate INT NOT NULL FOREIGN KEY
	REFERENCES Universitate(UnivID),
	NrCredite INT NOT NULL
	CONSTRAINT PK_Materie_Univ PRIMARY KEY
	(CodMaterie, CodUniversitate, NrCredite)
);