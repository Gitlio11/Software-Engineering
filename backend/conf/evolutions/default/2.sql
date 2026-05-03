# --- !Ups

create table ra_job_interview (
  id                            bigint auto_increment not null,
  rajob_application_id          bigint,
  interview_date                varchar(255),
  interview_time                varchar(255),
  location                      varchar(255),
  interview_type                varchar(255),
  notes                         varchar(1000),
  status                        varchar(255),
  created_time                  varchar(255),
  constraint pk_ra_job_interview primary key (id)
);

alter table ra_job_interview add constraint fk_ra_job_interview_application foreign key (rajob_application_id) references rajob_application (id) on delete restrict on update restrict;

# --- !Downs

alter table ra_job_interview drop foreign key fk_ra_job_interview_application;

drop table if exists ra_job_interview;
