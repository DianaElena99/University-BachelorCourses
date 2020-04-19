/*
*  SCRIPT SQL 
*/

---- > tabela care memoreaza versiunea bazei de date

GO
CREATE PROCEDURE usp_versions
AS BEGIN
	CREATE TABLE Ver(VID INT);
END

DROP TABLE VER;
EXEC usp_versions;

select * from ver;
insert into ver values (0);

-- -> modificare tip coloana
GO
CREATE PROCEDURE usp_procedure_1
AS
BEGIN
	ALTER TABLE Adresa
	ALTER COLUMN ZIP_Code INT;
END;

--EXEC usp_AlterColumn;

GO
CREATE PROCEDURE usp_undo_procedure_1
AS 
BEGIN
	ALTER TABLE Adresa
	ALTER COLUMN ZIP_Code VARCHAR(10);
	
END;

EXEC usp_undo_procedure_1

------------------------------------------------------------------------------------
---> adaugare constrangere de 'valoare implicita' pentru un camp

GO
CREATE PROCEDURE usp_procedure_2
AS
BEGIN 
	ALTER TABLE Adresa
	ADD CONSTRAINT def_city DEFAULT 'Cluj-Napoca' for City
END;

GO
CREATE PROCEDURE usp_undo_procedure_2
AS
BEGIN 
	ALTER TABLE Adresa
	DROP CONSTRAINT def_city 
END;


exec usp_undo_procedure_2
---------------------------------------------------------------------------------------
----> creare/stergere tabela

GO
CREATE PROCEDURE usp_procedure_3
AS 
BEGIN
	CREATE TABLE Studcard(ID INT PRIMARY KEY, StudID INT);
END


GO
CREATE PROCEDURE usp_undo_procedure_3
AS
BEGIN
	DROP TABLE Studcard;
END

exec usp_undo_procedure_3
---------------------------------------------------------------------------------------
----> adaugare camp nou

GO
CREATE PROCEDURE usp_procedure_4
AS
BEGIN
	ALTER TABLE BurseERASMUS
	ADD ID INT NOT NULL
END

GO
CREATE PROCEDURE usp_undo_procedure_4
AS
BEGIN
	ALTER TABLE BurseERASMUS
	DROP COLUMN ID
END

exec usp_undo_procedure_4
-----------------------------------------------------------------------------------------
---------> creare/stergere foreign key

GO
CREATE PROCEDURE usp_procedure_5
AS
BEGIN
	ALTER TABLE Decanat
	ADD CONSTRAINT fk_decan FOREIGN KEY (ID_Decan) REFERENCES Profesori(CodProf);
END

GO
CREATE PROCEDURE usp_undo_procedure_5
AS
BEGIN
	ALTER TABLE Decanat
	DROP CONSTRAINT fk_decan;
END


------------------------------------------------------------------------------------------
/* Procedura principala
 * Input : @nr - int; numarul versiunii la care se doreste sa se ajunga
			nr = {0,1,2,3,4,5}
	Output : baza de date in versiunea ceruta
			0 : versiunea initiala
			1 : modificare tip coloana in tabela Adresa (zip_code (varchar -> int))
			2 : default constraint in tabela Adresa (city default 'Cluj-Napoca')
			3 : creare tabela Studcard
			4 : adaugare coloana ID in tabela BurseErasmus
			5 : adaugare foreign key in tabela Decanat

*/


--drop procedure usp_main

GO
CREATE PROCEDURE usp_main
(@nr INTEGER)
AS 
BEGIN
	if @nr < 0 OR @nr > 5
		THROW 520000 , 'Nr Versiune invalida' , 1;

	DECLARE @curent INT;
	DECLARE @to_exec VARCHAR(50)

	-- selecteaza nr versiunii curente
	SELECT TOP 1 @curent = VID FROM Ver;
	print 'Versiunea curenta : ' + CAST(@curent as varchar(1)) 
	-- daca versiunea selectata este mai mare decat cea input
	-- efectueaza operatiile de undo

	if @curent > @nr
		while @curent > @nr
		begin
			set @to_exec = CAST(@curent as varchar(1))
			set @to_exec = CONCAT('usp_undo_procedure_', @to_exec)
			exec @to_exec
			UPDATE Ver SET VID = @curent
			set @curent = @curent - 1

		end;
	else
		while @curent < @nr 
			begin
				set @curent = @curent + 1
				set @to_exec = CAST(@curent as varchar(1))
				set @to_exec = CONCAT('usp_procedure_', @to_exec)
				exec @to_exec
				UPDATE Ver SET VID = @curent

			end;
	print 'Versiunea finala : ' + CAST(@curent as varchar(1)) 
	UPDATE Ver SET VID = @curent

END

--DROP PROCEDURE usp_main

--update ver set vid = 0

exec usp_main 0
exec usp_main 5
exec usp_main 3
exec usp_main 1

