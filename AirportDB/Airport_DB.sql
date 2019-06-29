drop database Airport;
create database Airport;
use Airport;
create table Planes(
id int not null primary key,
plane_name varchar(50) not null,
capacity int not null,
plane_type enum('passenger','cargo'));

create table Companies(
id int not null primary key,
name varchar(50));

create table Flights(
id int not null primary key auto_increment,
company_id int,
departure_town_id int not null,
arrival_town_id int not null,
departure_date date,
departure_time time,
arrival_date date,
arrival_time time,
price decimal(10,2));

set FOREIGN_KEY_CHECKS = 1;
alter table Flights add constraint FK_Flights_Departure_Towns foreign key Flights(departure_town_id) references Towns(id) 
on delete cascade
on update cascade; 

alter table Flights add constraint FK_Flights_Arrival_Towns foreign key Flights(arrival_town_id) references Towns(id) 
on delete cascade
on update cascade;

alter table Flights add constraint FK_Flights_Companies foreign key Flights(company_id) references Companies(id)
on delete cascade
on update cascade; 

create table Flights_Planes(
flight_id int not null,
plane_id int not null,
seat_num int,
constraint PK_Planes_Tris
primary key(flight_id));

drop table Flights_Planes;
alter table Flights_Planes add constraint FK_Flights_Planes_Planes foreign key Flights_Planes(plane_id) references Planes(id) 
on delete cascade on update no action;

alter table Flights_Planes add constraint FK_Flights_Planes_Flights foreign key Flights_Planes(flight_id) references Flights(id) 
on delete cascade on update no action;
	
create table Towns(
id int not null,
name varchar(20),
constraint PK_Towns
primary key(id));

create table Clients(
id int not null,
name varchar(20),
surname varchar(20),
cash decimal(10,2),
constraint PK_Clients
primary key(id));

create table Orders(
id int not null,
client_id int not null,
flight_id int not null,
constraint PK_Orders
primary key(id));

alter table Orders add constraint FK_Orders_Clients foreign key Orders(client_id) references Clients(id)
on delete cascade
on update cascade;

alter table Orders add constraint FK_Orders_Trips foreign key Orders(flight_id) references Flights(id)
on delete cascade
on update cascade;


insert Planes values(1,'Boeing777',200,'passenger');
insert Companies values(1,'Panamerica');
insert Towns values
(1,'London'),
(2,'Lviv');
insert Flights values(1,1,1,2,20190617,110000,20190619,195000,500.20);
insert Flights_Planes values(1,1,(select capacity from Planes where id = 1));

insert Clients values(1,'Igor','Pystovit',4000);

update Clients set cash = cash - (select price from Trips where departure_town_id = 1 and arrival_town_id = 2);

delimiter //
create procedure BuyTicket(departure_town varchar(50), arrival_town varchar(50),company_name varchar(50),client_id int)
begin
declare flight_id,departure_town_ID,arrival_town_ID,company_ID int;
set departure_town_ID = (select id from Towns where name like departure_town);
set arrival_town_ID = (select id from Towns where name like arrival_town);
set company_ID = (select id from Companies where name like company_name);
set flight_id = (select id from Flights where
departure_town_id = departure_town_ID
and
arrival_town_id = arrival_town_ID
and 
company_id = company_ID);
update Clients set cash = 
cash - (select price from Flights where id = flight_id);
update Flights_Planes set seat_num = seat_num -1;
insert Orders values(1,client_id,flight_id);
end //
delimiter ;

call BuyTicket('London','Lviv','Panamerica',1);drop database Airport;
create database Airport;
use Airport;
create table Planes(
id int not null primary key,
plane_name varchar(50) not null,
capacity int not null,
plane_type enum('passenger','cargo'));

create table Companies(
id int not null primary key,
name varchar(50));

create table Flights(
id int not null primary key auto_increment,
company_id int,
departure_town_id int not null,
arrival_town_id int not null,
departure_date date,
departure_time time,
arrival_date date,
arrival_time time,
price decimal(10,2));

set FOREIGN_KEY_CHECKS = 1;
alter table Flights add constraint FK_Flights_Departure_Towns foreign key Flights(departure_town_id) references Towns(id) 
on delete cascade
on update cascade; 

alter table Flights add constraint FK_Flights_Arrival_Towns foreign key Flights(arrival_town_id) references Towns(id) 
on delete cascade
on update cascade;

alter table Flights add constraint FK_Flights_Companies foreign key Flights(company_id) references Companies(id)
on delete cascade
on update cascade; 

create table Flights_Planes(
flight_id int not null,
plane_id int not null,
seat_num int,
constraint PK_Planes_Tris
primary key(flight_id));

drop table Flights_Planes;
alter table Flights_Planes add constraint FK_Flights_Planes_Planes foreign key Flights_Planes(plane_id) references Planes(id) 
on delete cascade on update no action;

alter table Flights_Planes add constraint FK_Flights_Planes_Flights foreign key Flights_Planes(flight_id) references Flights(id) 
on delete cascade on update no action;
	
create table Towns(
id int not null,
name varchar(20),
constraint PK_Towns
primary key(id));

create table Clients(
id int not null,
name varchar(20),
surname varchar(20),
cash decimal(10,2),
constraint PK_Clients
primary key(id));

create table Orders(
id int not null,
client_id int not null,
flight_id int not null,
constraint PK_Orders
primary key(id));

alter table Orders add constraint FK_Orders_Clients foreign key Orders(client_id) references Clients(id)
on delete cascade
on update cascade;

alter table Orders add constraint FK_Orders_Trips foreign key Orders(flight_id) references Flights(id)
on delete cascade
on update cascade;


insert Planes values(1,'Boeing777',200,'passenger');
insert Companies values(1,'Panamerica');
insert Towns values
(1,'London'),
(2,'Lviv');
insert Flights values(1,1,1,2,20190617,110000,20190619,195000,500.20);
insert Flights_Planes values(1,1,(select capacity from Planes where id = 1));

insert Clients values(1,'Igor','Pystovit',4000);

update Clients set cash = cash - (select price from Trips where departure_town_id = 1 and arrival_town_id = 2);

delimiter //
create procedure BuyTicket(departure_town varchar(50), arrival_town varchar(50),company_name varchar(50),client_id int)
begin
declare flight_id,departure_town_ID,arrival_town_ID,company_ID int;
set departure_town_ID = (select id from Towns where name like departure_town);
set arrival_town_ID = (select id from Towns where name like arrival_town);
set company_ID = (select id from Companies where name like company_name);
set flight_id = (select id from Flights where
departure_town_id = departure_town_ID
and
arrival_town_id = arrival_town_ID
and 
company_id = company_ID);
update Clients set cash = 
cash - (select price from Flights where id = flight_id);
update Flights_Planes set seat_num = seat_num -1;
insert Orders values(1,client_id,flight_id);
end //
delimiter ;

call BuyTicket('London','Lviv','Panamerica',1);
