drop table if exists todo;
create table todo (
    id bigint not null auto_increment,
    contents varchar(255),
    status varchar(255),
    title varchar(255),
    primary key (id)
) engine=InnoDB;