
drop table login;
create table login (
    id_login    number        not null,
    username    varchar2(20)  not null,
    password    varchar2(100) null,
    constraint login_pk primary key ( id_login ),
    constraint login_uk unique ( username )
);
drop sequence login_seq;
create sequence login_seq start with 1 increment by 1;
create or replace trigger login_insert_trg
before insert on login
referencing new as new old as old
for each row
begin
    select login_seq.nextval into :new.id_login from dual;
end;
insert into login ( username, password ) values  ( 'mdd', 'GBODcvrUuUUzzUiB8D3GxpKW3YlyNODO6D9yfi5rH2M=' );
insert into login ( username, password ) values  ( 'maddel', 'Q0SCP+ZsBBAurKE3kKAhlC30aUw5kTJuxD1IK2JMi/Y=' );
commit;
select * from login;

drop table kontakte;
create table kontakte (
    id_kontakte number        not null,
    firstname   varchar2(100) not null,
    lastname    varchar2(100) not null,
    phonenumber varchar2(20)  null,
    email       varchar2(100) null,
    constraint kontakte_pk primary key ( id_kontakte ),
    constraint kontakte_uk unique ( firstname, lastname )
);
drop sequence kontakte_seq;
create sequence kontakte_seq start with 1 increment by 1;
create or replace trigger kontakte_insert_trg
before insert on kontakte
referencing new as neu old as alt
for each row
begin
    select kontakte_seq.nextval into :neu.id_kontakte from dual;
end;
insert into kontakte ( firstname, lastname, phonenumber, email ) values  ( 'Maddel', 'Maddelsen', '', '' );
insert into kontakte ( firstname, lastname, phonenumber, email ) values  ( 'Maddel', 'Paddel', '123456', '' );
insert into kontakte ( firstname, lastname, phonenumber, email ) values  ( 'Kulle', 'Wolters', '6543/21', 'k.wolters@gmx.de' );
commit;
select * from kontakte;

