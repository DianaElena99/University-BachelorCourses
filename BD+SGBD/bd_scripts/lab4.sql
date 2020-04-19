/*
3 TABELE :

1. Tabel cu o cheie primara si o cheie straina
------------------------------------------------------
CREATE TABLE BOOKS
(ID INT NOT NULL PRIMARY KEY, 
TITLE VARCHAR(50), 
AUTHOR VARCHAR(50), 
CATEGORY INT NOT NULL);

2. Tabel cu cheie primara si fara cheie straina
-------------------------------------------------------
CREATE TABLE CATEGORIES
(ID_cat INT NOT NULL PRIMARY KEY, NUME (VARCHAR 50));

3. Tabel cu cheie primara compusa din 2 campuri
-------------------------------------------------------
CREATE TABLE LIBRARIES
(NUME VARCHAR(50),
CITY VARCHAR(50),
STREET VARCHAR(50)
PRIMARY KEY(NUME, CITY)
);

INSERT INTO TABLES(TableID, Name) values
('BOOKS'), ('CATEGORIES'), ('LIBRARIES');

VIEW-uri :

1. extrage date dintr-un tabel
----------------------------------
create view vw1 as 
select * from libraries;

2. extrage date din 2 tabele + clauza GROUP BY
----------------------------------------------
create view vw2 as
select author, count(id)as nr, Categories.Nume from books
inner join Categories on Categories.ID_cat = books.Category
group by author, Categories.Nume;

3. extrage date din 2 tabele
----------------------------------------------
create view vw3 as
select b.title, c.nume from Books b
inner join Categories c 
on c.ID_cat = b.Category;

*/


		-- TESTE PE TABELE

--------------------------------------------------------------------------

	-- adaugam multe date in tabelele books si categories

drop procedure test1;

go
create procedure test1
as begin
	declare @id int;
	set  @id = 1;

	declare @start datetime;
	declare @end datetime;
	set @start = GETDATE();

	while @id >= 1 and @id <= 10000
	begin

		declare @titlu varchar(30);
		declare @autor varchar(30);
		declare @cat int;
		declare @nraut int;
		declare @numecat varchar(30);

		set @nraut = @id % 90;
		set @titlu = 'titlu_' + cast(@id as varchar(10));
		set @autor = 'autor_' + cast(@nraut as varchar(10));
		set @cat = @id % 100;
		set @numecat = 'categorie_' + cast(@cat as varchar(10));

		if @id < 100
			begin
				insert into categories values (@cat, @numecat);		
			end

		insert into Books values (@id, @titlu, @autor, @cat);

		set @id = @id +1;

	end

	set @end = GETDATE();

	INSERT INTO TestRuns(Description, StartAt, EndAt) VALUES ('insert table books+categ',@START, @END);
	INSERT INTO Tests(Name) values ('insert table books+categ');
	
	
	INSERT INTO TestTables (TestID, TableID, NoOfRows, Position) values
	(1, 1, 10000, 2 ),(1, 2, 100,1);
	
	INSERT INTO TestRunTables (TestRunID, TableID, StartAt, EndAt) values 
	(1, 1, @start, @end),(1, 2, @start, @end);

	select * from TestRunTables;

	PRINT 'TEST1' ; PRINT DATEDIFF(second, @end, @start);

end

drop procedure test2;
	-- stergem datele inserate in test1
go
create procedure test2
as begin
	declare @start2 DATETIME;
	declare @end2 DATETIME;

	SET @start2 = GETDATE();
	DELETE FROM BOOKS;
	DELETE FROM categories;
	SET @end2 = GETDATE();
	
	INSERT INTO TestRuns(Description, StartAt, EndAt) VALUES ('delete books+cat',@start2,@end2 );
	INSERT INTO TESTS(Name) values ('delete books+cat');
	
	declare @tid int;
	select @tid = count(testID) from tests;

	INSERT INTO TestRunTables(TestRunID, TableID, StartAt,EndAt) VALUES
						(@tid, 1, @start2, @end2),(@tid, 2, @start2, @end2);
	insert into TestTables values (@tid, 1, 10000, 2), (@tid, 2, 100, 1);

	print 'TEST2' ; PRINT DATEDIFF(second, @end2, @start2);
end


	--- adaugam date in tabela Libraries

	drop procedure test3;
go
create procedure test3
as begin
	declare @start3 DATETIME;
	declare @end3 DATETIME;
	declare @id int;
	
	select @id = 1;
	SET @start3 = GETDATE();
	
	while @id >= 1 and @id <= 1000
	begin
		declare @nume varchar(30);
		declare @city varchar(30);
		declare @street varchar(30);
		declare @idd int;
		set @idd = @id % 20;
		set @nume = 'library_' + cast(@id as varchar(10));
		set @city = 'city_' + cast(@idd as varchar(10));
		set @street = 'street_' + cast(@idd as varchar(10));

		INSERT INTO Libraries values (@nume, @city, @street);

		set @id = @id + 1 ;
	end

	SET @end3 = GETDATE();
	declare @cnt int;
	select @cnt = count(TestRunid) from TestRuns;
	set @cnt = @cnt +1;
	print @cnt;

	insert into tests(Name) values ('insert libraries');
	insert into TestRuns(Description,StartAt,EndAt) values ('insert libraries', @start3, @end3);
	insert into TestTables(TestID, TableID,NoOfRows,Position) values (@cnt, 3, 1000, 1);
	insert into TestRunTables(TestRunID, TableID, StartAt,EndAt) values (@cnt, 3, @start3,@end3);
	print 'TEST3';
end;

	--- stergem date din tabela Libraries


go
create procedure test4
as begin
	declare @start4 datetime;	
	declare @end4 datetime;
	set @start4 = getdate();

	delete from libraries;
	set @end4 = getdate();
	
	insert into testruns values ('stergere_libraries', @start4, @end4);
	insert into tests values ('stergere_libraries');

	declare @tid int;
	select @tid = count(testid) from tests;
	print @tid;

	insert into TestRunTables(testrunid, TableID,StartAt,EndAt) values (@tid,3,@start4,@end4);
	insert into TestTables(testid,TableID,NoOfRows,Position) values (@tid, 3, 1000, 1);
	print 'TEST4';

end;

drop procedure test4;

select * from Tests;
select * from TestRuns;
select * from TestTables;
select * from TestRunTables;



			--- TESTE PE VIEWS

---------------------------------------------------------------------------------

go
create procedure testv1
as begin
	declare @start DATETIME; 
	declare @end DATETIME;
	set @start = GETDATE();
	
	select * from vw1;
	
	Set @end = GETDATE();
	print 'Test view 1'; print DATEDIFF(second,@START, @END);
	
	insert into tests(Name) values ('TestView1');
	insert into TestRuns(Description, StartAt,EndAt) VALUES ('TestView1', @start, @end);
	
	declare @tid int;
	select @tid = count(testid) from tests;

	insert into TestViews values (@tid,1);
	insert into TestRunViews values (@tid, 1, @start, @end);

end

go 
create procedure testv2
as begin
	declare @start DATETIME; 
	declare @end DATETIME;
	set @start = GETDATE();

	select * from vw2;

		Set @end = GETDATE();
	print 'Test view 2'; print DATEDIFF(second,@START, @END);
	
	insert into tests(Name) values ('TestView2');
	insert into TestRuns(Description, StartAt,EndAt) VALUES ('TestView2', @start, @end);
	
	declare @tid int;
	select @tid = count(testid) from tests;

	insert into TestViews values (@tid,2);
	insert into TestRunViews values (@tid, 2, @start, @end);
end


go create procedure testv3
as begin
	declare @start DATETIME; 
	declare @end DATETIME;
	set @start = GETDATE();
	
	select * from vw3;

	Set @end = GETDATE();
	print 'Test view 3'; print DATEDIFF(second,@START, @END);
	
	insert into tests(Name) values ('TestView3');
	insert into TestRuns(Description, StartAt,EndAt) VALUES ('TestView3', @start, @end);
	
	declare @tid int;
	select @tid = count(testid) from tests;

	insert into TestViews values (@tid,1);
	insert into TestRunViews values (@tid, 3, @start, @end);
end


-----------------------------------------------------------------


-- executam adaugarile in tabele 
exec test1;
exec test3;

-- testam view-urile 
exec testv1;
exec testv2;
exec testv3;

-- stergem datele
exec test2;
exec test4;

delete from books;
delete from categories;

delete from Libraries;

delete from tests;
delete from TestRuns;
delete from TestRunTables;
delete from TestTables;

select * from tests;
select * from TestRuns;
select * from TestRunTables;
select * from TestTables;
select * from TestRunViews;
select * from TestViews;

select * from Tables;
select * from Views;

insert into tables values ('books'),('categories'),('libraries');
insert into views values ('vw1'),('vw2'),('vw3');

drop table tests;
drop table TestRuns;
drop table TestRunTables;
drop table TestRunViews;
drop table TestTables;
drop table TestViews;