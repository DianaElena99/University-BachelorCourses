
create table medicament(
idm int identity primary key,
nume varchar(50) not null,
expirare date not null
)

create table cabinet (
id_cab int identity primary key,
nume varchar(50) not null,
adresa varchar(50) not null
)

create table doctori(
id_doc int identity primary key,
nume varchar(50) not null,
functie varchar(50) not null,
)

/*Relatie m-n intre medic si cabinet*/
create table medicCabinet(
id_medic int not null,
id_cabinet int not null,
primary key(id_medic, id_cabinet),
foreign key(id_medic) references doctori(id_doc),
foreign key(id_cabinet) references cabinet(id_cab)
)

create table farmacie(
idf int identity primary key,
nume varchar(50) not null,
codpostal int not null
)


/*Relatie m-n intre farmacie si medicamente */
create table medicamentLaFarmacie(
id_medicament int,
id_farmacie int,
primary key(id_medicament, id_farmacie),
foreign key(id_medicament) references medicament(idm),
foreign key(id_farmacie) references farmacie(idf)
)

/*relatie m-n intre reteta si medicament : un medicament poate fi in mai mutle retete,
o reteta are m multe medicamnete*/
create table reteta(
idr int identity primary key
)

create table medicamentInReteta(
id_reteta int not null,
id_medicament int not null,
primary key(id_reteta, id_medicament),
foreign key(id_reteta) references reteta(idr),
foreign key(id_medicament) references medicament(idm)
)

/*
	2. 
*/



go
create procedure adaugaMedicamentFarmacie(
@numef varchar(50),
@numem varchar(50),
@data varchar(50)
) as begin

	declare @data2 date
	set @data2 = cast(@data as date)

	declare @idm int
	declare @idf int

	if @numef not in (select nume from medicament)
		begin
		insert into medicament(nume, expirare) values (@numem, @data2)
		end

	if @numef in (select m.nume from medicamentLaFarmacie mf inner join medicament m on mf.id_medicament = m.idm ) 
		begin
		update medicament set expirare = @data2 where medicament.nume = @numem
		end
	else

		begin
			select @idm = idm from medicament where medicament.nume = @numem
			select @idf = idf from farmacie where farmacie.nume = @numef
			insert into medicamentLaFarmacie values (@idm, @idf)
		end
end

	exec adaugaMedicamentFarmacie 'tei', 'medicament', '2020-12-21'
	select * from medicament
	select * from medicamentLaFarmacie

	
/*
	3.
*/
	
	go

	create view vw_Medicamente as
	select m.nume from medicament m where m.idm not in (select mf.id_medicament from medicamentLaFarmacie mf);

	select * from vw_Medicamente

/*
	4.
*/



	go

	create function medicamenteExpirate(
		@E varchar(50),
		@N int
	) returns @rezultat table(nume varchar(50))
	as begin
		declare @data date
		set @data = cast(@E as date)
		declare @aux table(id int, nume varchar(50), expirare date)

		insert into @aux(id, nume, expirare) select count(mf.id_farmacie), m.nume, m.expirare as numar_farmacii from medicament m 
									inner join medicamentLaFarmacie mf on mf.id_medicament = m.idm
									group by m.nume, m.expirare
		insert into @rezultat(nume) select distinct a.nume from @aux a where datediff(day, @data, a.expirare) > 0 and a.id >= @N
		return 
	end

	go

	select * from dbo.medicamenteExpirate( '2019-10-10', 2)
	
	exec adaugaMedicamentFarmacie 'alma', 'ibuprofen', '2019-12-20'
	
	select * from medicament
	select * from medicamentLaFarmacie

