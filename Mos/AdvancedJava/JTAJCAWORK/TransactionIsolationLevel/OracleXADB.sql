--in Database One
drop table cart;
drop table item;

create table cart (
cart number not null primary key,
ordered date not null )
tablespace users pctfree 20
storage (initial 4K next 4K pctincrease 0);

create table item (
item number not null primary key,
cart number not null,
quantity number not null,
descr varchar2(30) not null,
each number not null )
tablespace users pctfree 20
storage (initial 4K next 4K pctincrease 0);
commit;

--in Database Two
drop table shipping;
drop table billing;

create table shipping (
cart number not null primary key,
name varchar2(30) not null,
address1 varchar2(30) not null,
address2 varchar2(30),
city varchar2(30) not null,
state varchar2(30),
country varchar2(30) not null,
postal varchar2(30) not null,
carrier varchar2(30) not null,
service varchar2(30) not null )
tablespace users pctfree 20
storage (initial 4K next 4K pctincrease 0);

create table billing (
cart number not null primary key,
name varchar2(30) not null,
card varchar2(30) not null,
expires date not null,
amount number not null )
tablespace users pctfree 20
storage (initial 4K next 4K pctincrease 0);
commit;	