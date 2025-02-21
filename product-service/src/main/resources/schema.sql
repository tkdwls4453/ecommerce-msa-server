DROP TABLE IF EXISTS shoes;
DROP TABLE IF EXISTS shoes_model;

CREATE TABLE shoes_model (
    model_id bigint not null auto_increment,
    price decimal(38,2),
    shoes_name varchar(255),
    primary key (model_id)
) engine=InnoDB;


CREATE TABLE shoes (
    shoes_id bigint not null auto_increment,
    quantity integer,
    model_id bigint,
    color enum ('BLACK','BLUE','GREEN','ORANGE','PURPLE','RED','WHITE','YELLOW'),
    size enum ('SIZE_220','SIZE_230','SIZE_240','SIZE_250','SIZE_260','SIZE_270','SIZE_280','SIZE_290'),
    primary key (shoes_id)
) engine=InnoDB;

alter table shoes add constraint shoes_foreign_key foreign key (model_id) references shoes_model (model_id);