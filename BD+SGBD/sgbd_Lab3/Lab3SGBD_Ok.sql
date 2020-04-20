-- Insert Note, Student, Materie
-- Validare date
-- Cauta student dupa nume -- daca nu exista, se adauga 
-- Cauta Materie dupa nume -- daca nu exista, se adauga
-- Insereaza Nota 
drop procedure AddNota

go
create procedure AddNota
(
@numeStud varchar(50),
@emailStud varchar(50),
@numeUniv varchar(50),

@numeMaterie varchar(50),
@numeDomeniu varchar(50),
@nivel varchar(30),

@nota float
)
as 
begin 
-- begin transaction
-- enter try block


	-- foreign keys
	declare @idUniv int
	declare @idDomeniu int

	--primary keys
	declare @idMat int
	declare @idStud int

	-- procesare date student

	declare @OkStud int
	set @OkStud = 1

	set @idMat = -1
	set @idStud = -1
	set @idUniv = -1
	set @idDomeniu = -1

	set @idUniv = (select id from universitate where nume = @numeUniv)
	print @idUniv

	select @idStud = id from Students where nume = @numeStud
	print @idStud
	

begin try
begin tran
	-- valid numeUniv
	if @idUniv = -1
		begin
			set @OkStud = 0
			print 'nume universitate invalid'
			RAISERROR('nume universitate invalid',14,1)
		end

	-- valid email stud
	if @emailStud not like '_%@%.%'
		begin 
			set @OkStud = 0
			print 'email invalid'
			raiserror ('email invalid -- trebuie sa fie de forma asdfg@asd.as', 14, 1)
		end

	--valid nume stud
	if @numeStud = ''
	begin
		set @OkStud = 0
		print 'nume stud nu poate fi vid'
		raiserror('nume student vid', 14,1)
	end

	--daca toate validarile sunt ok si studentul nu exista in baza de date
	if @OkStud = 1 and @idStud = -1
	begin
		insert into students(univID, nume, email) values (@idUniv, @numeStud, @emailStud)
		set @idStud = (select id from students where nume = @numeStud)
		commit tran

	end
	end try

--begin catch
-- end tran	
	begin catch
		rollback tran
		print 'transaction rolling back from add student'
	end catch

	-- procesare date materie

	declare @OkMaterie int 
	set @OkMaterie = 1

--begin try, begin trans

begin try
begin tran

	select @idMat = id from materie where nume = @numeMaterie
	select @idDomeniu = id from domeniu where numeDomeniu = @numeDomeniu

	if @idDomeniu = -1
	BEGIN
		set @OkMaterie = 0
		print ' --- domeniu inexistent'
		raiserror('domeniu invalid', 14,1)
	END

	if @numeMaterie = ''
	begin
		set @OkMaterie = 0
		print ' --- nume materie invalid'
		raiserror ('nume invalid', 14,1)
	end

	if @nivel not in ('licenta', 'master', 'postuniversitar')
		begin
			raiserror ('nivel trebuie sa fie unul dintre <licenta>, <master>, <postuniveristar>',14,1)
		end

	if @idMat = -1 and @OkMaterie = 1
	begin
		insert into materie(domeniu, nume, nivel) values (@idDomeniu, @numeMaterie, @nivel)
		set @idMat = (select id from materie where nume=@numeMaterie )
		commit tran		
	end
end try
	
begin catch
	rollback tran
	print 'transaction rolling back from add materie'
end catch

	-- adaugare in tabela de legatura

begin try
	declare @okNota int
	set @okNota = 1
	IF @nota < 1 or @nota > 10
	begin
		set @okNota = 0
		raiserror ('nota trebuie sa fie >= 1 si =< 10',14,1)
	end
	else
	print 'nota e ok'

	if @okNota = 1 and @idMat != -1 and @idStud != -1
		begin
			begin tran
			insert into NotaStudent(idMaterie, idStudent, nota) values (@idMat, @idStud, @nota)
			commit tran
		end
	
end try
begin catch
	print @idStud
	print @idMat
	print @nota
	print 'rolling back from add nota'
	rollback tran
end catch
end


-- cazuri care merg oki

exec AddNota 'vijai', 'vijai@gmail.com', 'Universitatea Tehnica', 'plf', 'computer science', 'licenta', 8.2
exec AddNota 'gicu', 'gicuDobre@gmail.com', 'Universitatea Babes Bolyai',  'plf', 'computer science', 'licenta', 6.4
exec AddNota 'zoli', 'zoltann@gmail.com', 'Universitatea Babes Bolyai',  'cloud comp', 'computer science', 'master', 9.5
select * from students
select * from NotaStudent
select * from materie

-- ăsta tre să dea fail la adăugarea de student dar sa se adauge materia 
-- nu se adaugă nota din moment ce stud nu există

exec AddNota 'micu', 'emailBengos', 'fara facultate', 'web cu bufny' , 'computer science', 'master', 4.99
select * from students
select * from NotaStudent
select * from materie

-- ăsta tre să dea fail la adăugarea de materie dar se adauga studentul 
-- nu se adaugă nici nota din moment ce materia nu a fost adăug.

exec AddNota 'micu al doilea', 'micu2@gmail.com', 'Universitatea Tehnica', 'meseria bratara de aur' , 'de toate', 'prescolar', 4.99
select * from students
select * from NotaStudent
select * from materie