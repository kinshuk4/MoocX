--ON DB1
create table cart (
cart bigint not null primary key,
ordered date not null )
;
create table item (
item bigint not null primary key,
cart bigint not null,
quantity int not null,
descr varchar(30) not null,
eachone int not null )
;
--ON DB2
create table shipping (
cart bigint not null primary key,
name varchar(30) not null,
address1 varchar(30) not null,
address2 varchar(30),
city varchar(30) not null,
state varchar(30),
country varchar(30) not null,
postal varchar(30) not null,
carrier varchar(30) not null,
service varchar(30) not null )
;
create table billing (
cart bigint not null primary key,
name varchar(30) not null,
card varchar(30) not null,
expires date not null,
amount int not null )
;