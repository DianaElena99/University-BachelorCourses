
-----------------------------------------------------------------------

	-- OPERATII CRUD PT TABELA STUDENTI

/*
	Functie pt validarea studentului
	Input : @id - id-ul studentului
			@prog - codul programului de studiu urmat de student
			@nrCredits - nr de credite de pe semestrul precedent
	Output : true - daca datele sunt valide
				false altfel
*/

-- drop function validator_stud;

go
create function validator_stud(
	@id INT, @uniid int, @prog int, @nrCredits int
)
returns bit as
begin
	declare @valid bit;
	set @valid = 1;
	
	if @id < 0 or exists (select 1 from studenti where CodStud = @id)
		begin
		set @valid = 0;
		end
	
	if @uniid < 0 or not exists (select 1 from Universitate where UnivID = @uniid)
		begin 
		set @valid = 0
		end

	if @prog < 0 or not exists(select UniID from ProgrameDeStudiu where idb = @prog)
	begin
		set @valid = 0;
	end

	if @nrCredits < 0 or @nrCredits > 40
	begin
		set @valid = 0
	end
	return @valid
end

-----------------------------------------------------------------------------------

/*	CREATE STUDENT */

go
create procedure insert_studenti (
	@id INT,
	@uniid int,
	@nume VARCHAR(50),
	@prenume varchar(50),
	@email varchar(50),
	@phone int,
	@prog int,
	@nrCredits int
) as begin

	declare @buget int, @bursa int;
	declare @valid bit;
	set @valid = 1;
	if @nrCredits = 30
		begin
		set @buget = 1;
		set @bursa = 700;
		end
	else
		begin
		set @buget = 0;
		set @bursa = 0;
		end

	set @valid = dbo.validator_stud(@id,@uniid ,@prog, @nrCredits)
	if @valid = 1
		insert into studenti values (
		@id, @uniid, @nume, @prenume, @email, @phone, @prog, @nrCredits, @bursa, @buget
	);
	else
		print 'Student invalid'

end;

select * from studenti

-- Teste
exec insert_studenti 10, 1, 'Petrusel', 'Serban', 'serby99@gmail.com', 78645323, 1 , 30 
exec insert_studenti 11, 4, 'Andreescu' , 'David', 'andrdavid99@gmail.com', 78465321, 20, 25
exec insert_studenti 12, 4, 'Apetrei', 'Stefan', 'stefanaptr@gmail.com', 7846513, 7, 24
exec insert_studenti 13, 1, 'Pintea', 'Gabi', 'gabipt99@gmail.com', 85632302, 1, 30
exec insert_studenti 14, 1, 'Margelatu', 'Costel', 'cstl99@gmail.com', 7986453, 1, 28
exec insert_studenti 15, 1, 'Caragialescu', 'Cornel', 'caragiale99@gmail.com', 86452122, 2, 30
exec insert_studenti 16, 1, 'ion', 'ionel', 'sdfg', 845321325, 3, 29
exec insert_studenti 17, 1, 'mihnea', 'mihai', 'waesd', 79864531, 3, 29
exec insert_studenti 18, 1, 'student', 'fara bani', 'sfdtyr', 79864532, 3, 25
exec insert_studenti 19, 1, 'badea', 'cioban', 'adresadegmail@yahoo.com' ,864532355, 1, 26
exec select_student 0
-----------------------------------------------------------------------------------------

	/* READ STUDENT (ID : int)
		ID = 0 => afiseaza toti studentii
		ID != 0 && valid => afis. studentul cu codul ID;
		altfel eroare
	*/

go
create procedure Select_Student(
	@param int
)as begin
	if @param = 0
		select * from Studenti;
	else if @param < 0 
		print 'introduceti un id valid'
	else if not exists (select 1 from Studenti where CodStud = @param)
		print 'Nu exista student'
	else select * from studenti where CodStud = @param
end;


--drop procedure Select_Student;

-- Teste
exec Select_Student 0;
exec Select_Student 5;
exec Select_Student 51;
exec Select_Student -551;

------------------------------------------------------------------------------

	/*	 DELETE STUDENT  */

	go
create procedure delete_student (
	@id int
)
as begin
	declare @nume varchar(50)
	if @id < 0 or not exists (select * from Studenti where CodStud = @id)
		print 'ID invalid'

	else
		begin
			-- Relatie Many To Many, mai intai stergem notele asociate studentului
		delete from NotaMaterie where Student = @id

		select @nume = nume from studenti where CodStud = @id;
		print 's-a sters studentul ' + @nume

		delete from studenti where CodStud = @id;
		end
end

-- Teste
select * from Studenti
exec delete_student 10
EXEC delete_student -100
------------------------------------------------------------------------------------

/*		UPDATE STUDENT		*/

go
create procedure update_student 
(
	@id int,
	@nrCredite int,
	@email varchar(50),
	@telefon int
)
as begin
	if @id < 0 or not exists (select * from Studenti where CodStud = @id)
		raiserror ('id invalid', 11,1)
	if @nrCredite > 40 or @nrCredite < 0
		raiserror ( 'nr invalid de credite',11,1)
	if @email is null 
		raiserror ('emailul nu poate fi nul',11,1)
	if @telefon < 0
		raiserror ('nr telefon invalid',11,1)
	update studenti set NumberOfCreditsLastSemester = @nrCredite,
						Email = @email, 
						Phone = @telefon
					where CodStud = @id
	select * from studenti where CodStud = @id;
end

select * from Studenti;
-- teste
exec update_student 1, 30, 'popion@gmail.com', 72356576
exec update_student 2, 25, 'ppandrei@gmail.com', 7846513
exec update_student 3, 30, 'mihneap', 78454512
exec update_student 5, 30, 'mih99@gmail.com' , 78451321
exec update_student 6, 30, 'aionut99@gmail.com', 78645321
exec update_student 8, 30, 'mzt@gmail.com', 85632212
exec update_student 9, 25, 'sadf@gmail.com', 78653223
-----------------------------------------------------------------------------------------------------------------

	-- Operatii CRUD pt tabela PROFESORI
go
create function validator_prof(
	@id int,
	@field varchar(50),
	@uniid int,
	@grad varchar(20),
	@nrCourses int,
	@nume varchar(50)
)
returns bit
as begin
	declare @valid bit
	set @valid = 1
	if @id < 0 or exists (select * from Profesori where CodProf = @id)
		set @valid = 0
	if not exists (select * from Domenii where NumeDomeniu = @field)
		set @valid = 0
	if not exists (select * from Universitate where UnivID = @uniid)
		set @valid = 0
	if @grad not in ('Lector', 'Conferentiar', 'Profesor', 'Asistent')
		set @valid = 0
	if @nrCourses < 1 
		set @valid = 0
	if @nume is null
		set @valid = 0
	return @valid
end

----------------------------------------------------------------------------------------------

go
create function valid_id_prof(@id int) returns bit
as begin
	declare @valid int 
	set @valid = 1
	if @id < 0 or not exists(select * from Profesori where CodProf = @id)
		set @valid = 0
	return @valid
end

----------------------------------------------------------------------------------------------

go 
create procedure insert_prof(
	@id int,
	@field varchar(50),
	@uniid int,
	@grad varchar(50),
	@nrCourses int,
	@nume varchar(50)
)as
begin
	declare @valid bit
	set @valid = dbo.validator_prof(@id, @field, @uniid, @grad, @nrCourses, @nume)
	if @valid = 1
		insert into profesori values (@id, @field, @uniid, @grad, @nrCourses, @nume)
	else print 'Prof invalid'
end

select * from profesori
exec insert_prof 334, 'ComputerScience', 8, 'Lector' , 2, 'Alex Pop' 

---------------------------------------------------------------------------------------------

go
create procedure select_prof (@id int)
as begin
	if @id = 0 
		select * from Profesori;
	else
		begin
			declare @valid bit
			set @valid = dbo.valid_id_prof(@id)
			if @valid = 1
				select * from Profesori where CodProf = @id
			else
				print 'id invalid'
		end
end

exec select_prof 0
exec select_prof 222
-----------------------------------------------------------------------------------------------

go
create procedure delete_prof (@id int)
as begin
	 if exists(select 1 from Profesori where CodProf = @id)
		begin
			delete from decanat where id_Decan = @id 
			delete from Profesori where CodProf = @id 
			SELECT * from Profesori
		end
	else
		print 'id invalid'
end

exec delete_prof 334
exec delete_prof 1999
----------------------------------------------------------------------------------------------

go create procedure update_prof (
	@id int,
	@grad varchar(20),
	@nrCourses int,
	@nume varchar(50)
) as begin
	declare @valid bit
	set @valid = 1
	set @valid = dbo.valid_id_prof(@id)
	if @grad not in ('Lector', 'Conferentiar', 'Profesor', 'Asistent')
		set @valid = 0
	if @nrCourses < 1 or @nrCourses > 3
		SET @vALID = 0
	if @nume is null
		set @valid = 0
	if @valid = 1
		update Profesori set Grad = @grad, NrCoursesPerSemester = @nrCourses,
							 nume = @nume 
						where CodProf = @id
	select * from Profesori where CodProf = @id 
end


exec update_prof 10,'Conferentiar',2, 'Berinde Stefan'

------------------------------------------------------------------------------------------------------------

		/*	CRUD   OPERATIONS  FOR  NotaMaterie   !-- Relatie Many To Many */

go
create function validare_nota(@nota float, @stud int, @mat int)
returns bit as 
begin 
	declare @valid bit
	set @valid = 1
	if @nota < 0 or @nota > 10
		begin
		set @valid = 0
		end
	if @stud < 0 or not exists (select * from Studenti where CodStud = @stud)
		begin
		set @valid = 0
		end
	if @mat < 0 or not exists (select * from Materii where CodMaterie = @mat)
		begin
		set @valid = 0
		end
	return @valid
end


/*
	Functie de validare a id-ului
	returneaza 0/False daca id-ul este numar negativ sau daca exista in tabel
				1/True altfel
*/

go

create function exists_id_nota(@id int)
returns bit as
begin
	if exists(select * from NotaMaterie where id_nota = @id)
		return 1
	return 0
end

go
create function positive_id_nota(@id int)
returns bit as
begin
	declare @valid bit
	set @valid = 1
	if @id < 0 
		set @valid = 0
	return @valid
end


go
create procedure insert_nota(
	@id_nota int,
	@nota float,
	@stud int,
	@materie int
)as
begin
	declare @valid_id bit
	declare @valid_fields bit
	set @valid_fields = dbo.validare_nota(@nota, @stud, @materie)

	if @valid_fields = 1 and dbo.positive_id_nota(@id_nota) = 1 and dbo.exists_id_nota(@id_nota) = 0
		begin
		insert into NotaMaterie values (@id_nota, @stud, @materie, @nota)
		select * from NotaMaterie
		end
end


exec insert_nota 5,9.5,1,23
exec insert_nota 6,7.5,2,12

go
create procedure delete_nota(
	@id_nota int
)as begin
	declare @valid bit
	set @valid = 1
	if not exists (select 1 from NotaMaterie where id_nota = @id_nota)
		set @valid = 0
	if @valid = 1
		begin
		print 'S-a sters nota ' 
		select * from NotaMaterie where id_nota = @id_nota
		delete from NotaMaterie where id_nota = @id_nota
		end
end

exec delete_nota 5


go
create procedure update_nota(
	@id_nota int,
	@new_nota float
)as begin
	if @new_nota < 0 or @new_nota > 10
		raiserror ('Nota invalida',11,1)
	else if dbo.positive_id_nota(@id_nota) = 1 and dbo.exists_id_nota(@id_nota) = 1
		begin
		update NotaMaterie set nota = @new_nota where id_nota = @id_nota
		select * from NotaMaterie where id_nota = @id_nota
		end

end

exec insert_nota 5, 10, 1, 23
exec update_nota 5, 8.5
--------------------------------------------------------------------------------------------------------------

		/*	CRUD OPERATIONS FOR Materii	*/


go
create function exists_id_materie(@codMaterie int)
returns bit
as begin
	declare @valid bit
	if exists(select * from Materii where CodMaterie = @codMaterie)
		set @valid = 1
	else
		set @valid = 0
	return @valid
end


go
create function positive_id(@codMaterie int)
returns bit
as begin
	declare @valid bit
	if @codMaterie < 0
		set @valid = 0
	else set @valid = 1
	return @valid
end

go 
create function validare_materie(@nume varchar(50), @domeniu varchar(50), @prog int)
returns bit
as begin
	declare @valid bit
	if @nume is null
		return 0
	if not exists (select 1 from domenii where NumeDomeniu = @domeniu)
		return 0
	if not exists (select 1 from ProgrameDeStudiu where IDB = @prog)
		return 0
	return 1
end


go 
create procedure insert_materie(
	@codMaterie int,
	@nume VARCHAR(50),
	@domeniu VARCHAR(50),
	@prog int
)as begin
	declare @valid bit
	set @valid = 1
	set @valid = dbo.validare_materie(@nume, @domeniu, @prog)
	if @valid = 1 and dbo.positive_id(@codMaterie) = 1 and dbo.exists_id_materie(@codMaterie) = 0 
		begin
		insert into materii values (@codMaterie, @nume, @domeniu, @prog)
		select * from materii where CodMaterie = @codMaterie
		end
end

exec insert_materie 17, 'Metode Avansate de Programare', 'ComputerScience', 1

drop procedure delete_materie

go
create procedure delete_materie(
	@codmaterie int
)
as begin
	if dbo.exists_id_materie(@codmaterie) = 1 and dbo.positive_id(@codmaterie) = 1
		begin
		delete from NotaMaterie where materie = @codmaterie
		select * from Materii where CodMaterie = @codmaterie
		delete from Materii where CodMaterie = @codmaterie
		end
	else print 'Nu s-a putut sterge materia. Dati un id valid'
end

exec delete_materie 17


go
create procedure select_materie(@id int)
as begin
	if @id  = 0
		select * from Materii
	else if @id > 0 and exists (select 1 from materii where codmaterie = @id)
		select * from Materii where CodMaterie = @id
	else
		print 'Materie inexistenta'
end

go
create procedure update_materie(
	@codMaterie int,
	@nume varchar(50),
	@domeniu varchar(50),
	@prog int
)as begin
	if dbo.exists_id_materie(@codMaterie) = 1 and dbo.positive_id(@codMaterie) = 1
					and dbo.validare_materie(@nume, @domeniu,@prog) = 1
		begin
		delete from materii where CodMaterie = @codMaterie
		insert into materii values (@codMaterie, @nume, @domeniu, @prog)
		end
end
--------------------------------------------------------------------------------------------------------------

		/*  CRUD OPERATIONS FOR Decanat */

---------- Validatori

go
create function positive_id_decan(
	@id int
)
returns bit
as begin
	if @id < 0 
		return 0
	return 1
end


go 
create function exists_id_decan(
	@id int
)returns bit
as begin
	if exists(select * from Decanat where ID_Decan = @id)
		return 1
	return 0
end


go
create function validate_decan(
	@id int,
)returns bit
as begin
	if not exists (select * from profesori where CodProf = @id)
		return 0
	return 1
end

------------------------------------------------------------------------------------

go
create procedure insert_decan(
	@id_decan int
) as begin
	declare @univ varchar(50)
	declare @uid int
	select @uid = uniid from Profesori where CodProf = @id_decan
	select @univ = nume from Universitate where UnivID = @uid 
	if not exists (select 1 from decanat where ID_Decan = @id_decan and Univ = @univ)
		insert into decanat(Univ, ID_Decan) values (@univ, @id_decan);
end


select * from profesori
exec insert_decan 12
exec insert_decan 15
select * from Decanat

---------------------------------------------------------------------------------------------

go
create procedure delete_decan
(@id_decan int)
as begin
	if dbo.exists_id_decan(@id_decan)=1 and dbo.positive_id_decan(@id_decan)=1 
		delete from decanat where ID_Decan = @id_decan
end

exec delete_decan 12


---------------------------------------------------------------------------------------------------------------------------

 /*    VIEWS, INDEXES */ 

 go
 create view vw_decanat as 
 select * from decanat 

 go
 create view vw_note as 
 select * from NotaMaterie


 go 
 create view vw_stud as 
 select CodStud, uniid, nume, prenume from studenti

 go
 create view vw_prof as
 select * from profesori

 go
 create view vw_nm as 
 select nm.nota, nm.student, m.numeMaterie, s.nume, s.prenume from NotaMaterie nm
 inner join Materii m on nm.materie = m.CodMaterie 
 inner join Studenti s on s.CodStud = nm.student where s.ProgramAttending = 1

 select * from vw_nm

 go
 create view vw_note_materii as
 select nm.nota, nm.student, m.numeMaterie from NotaMaterie nm
 inner join Materii m on nm.materie = m.CodMaterie

 select * from vw_note_materii

 go
 create view vw_decani_profi as
 select p.nume, p.field, p.grad, d.univ from Decanat d 
 inner join Profesori p on p.CodProf = d.ID_Decan

 select * from vw_decani_profi


 select * from vw_note
 select * from vw_decanat
 select * from vw_materii
 select * from vw_prof
 select * from vw_stud

 go
 create nonclustered index idx_decan on Decanat(univ asc)

 go
 create nonclustered index idx_note on NotaMaterie(student asc, nota asc) include (id_nota, materie)

 drop index idx_stud_2 on studenti

 go 
 create nonclustered index idx_stud_2 on Studenti(codstud asc, programattending asc) include (nume,prenume,uniid, email, phone, buget, bursa)

 go
 create nonclustered index idx_materie on Materii(codMaterie asc, domeniu asc, programDeStudiu asc) include (numematerie)

 go 
 create nonclustered index idx_prof on Profesori(codProf asc, grad asc) include
 (field, uniid, nrcoursespersemester, nume)
