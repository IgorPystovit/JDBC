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

drop table Planes_Companies;
create table Planes_Companies(
id int not null,
company_id int not null,
plane_id int not null,
available_seats int,
constraint PK_Planes_Companies
primary key Planes_Companies(id));

alter table Planes_Companies add constraint FK_Planes_Companies_Companies foreign key Planes_Companies(company_id)
references Companies(id) 
on delete cascade
on update cascade;

alter table Planes_Companies add constraint FK_Planes_Companies_Planes foreign key Planes_Companies(plane_id)
references Planes(id) 
on delete cascade
on update cascade;

set FOREIGN_KEY_CHECKS = 1;

drop table Flights;
create table Flights(
id int not null primary key auto_increment,
company_id int,
departure_town_id int not null,
arrival_town_id int not null,
departure_date date,
departure_time time,
arrival_date date,
arrival_time time,
plane_id int not null,
price decimal(10,2));

alter table Flights add constraint FK_Flights_Departure_Towns foreign key Flights(departure_town_id) references Towns(id) 
on delete cascade
on update cascade; 

alter table Flights add constraint FK_Flights_Arrival_Towns foreign key Flights(arrival_town_id) references Towns(id) 
on delete cascade
on update cascade;

alter table Flights add constraint FK_Flights_Companies foreign key Flights(company_id) references Companies(id)
on delete cascade
on update cascade; 

alter table Flights add constraint FK_Flights_Planes foreign key Flights(plane_id) 
references Planes_Companies(id)
on delete cascade
on update cascade;

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
dateT datetime,
constraint PK_Orders
primary key(id));

alter table Orders add constraint FK_Orders_Clients foreign key Orders(client_id) references Clients(id)
on delete cascade
on update cascade;

alter table Orders add constraint FK_Orders_Trips foreign key Orders(flight_id) references Flights(id)
on delete cascade
on update cascade;

alter table Orders add constraint FK_Orders_Trips foreign key Orders(flight_id) references Flights(id)
on delete cascade
on update cascade;
