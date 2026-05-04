# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table author (
  id                            bigint auto_increment not null,
  author_name                   varchar(255),
  first_name                    varchar(255),
  last_name                     varchar(255),
  middle_initial                varchar(255),
  affiliation                   varchar(255),
  title                         varchar(255),
  email                         varchar(255),
  mailing_address               varchar(255),
  phone_number                  varchar(255),
  research_fields               varchar(255),
  highest_degree                varchar(255),
  level                         varchar(255),
  homepage                      varchar(255),
  avatar                        varchar(255),
  create_time                   varchar(255),
  is_active                     varchar(255),
  rating                        double not null,
  rating_count                  bigint not null,
  recommend_rating              double not null,
  recommend_rating_count        bigint not null,
  constraint pk_author primary key (id)
);

create table author_paper (
  author_id                     bigint,
  paper_id                      bigint
);

create table bug_report (
  id                            bigint auto_increment not null,
  title                         varchar(255),
  description                   varchar(255),
  solved                        integer not null,
  create_time                   datetime,
  solve_time                    datetime,
  long_description              varchar(255),
  reporter_id                   bigint,
  fixer_id                      bigint,
  constraint pk_bug_report primary key (id)
);

create table challenge (
  id                            bigint auto_increment not null,
  challenge_title               varchar(255),
  short_description             varchar(255),
  long_description              varchar(255),
  required_expertise            varchar(255),
  preferred_expertise           varchar(255),
  preferred_time                varchar(255),
  tech                          varchar(255),
  time                          varchar(255),
  budget                        varchar(255),
  location                      varchar(255),
  organizations                 varchar(255),
  status                        varchar(255),
  create_time                   varchar(255),
  update_time                   varchar(255),
  min_budget                    integer not null,
  max_budget                    integer not null,
  challenge_pdf                 varchar(255),
  challenge_image               varchar(255),
  rating                        double not null,
  rating_count                  bigint not null,
  recommend_rating              double not null,
  recommend_rating_count        bigint not null,
  homepage                      varchar(255),
  avatar                        varchar(255),
  created_time                  varchar(255),
  is_active                     varchar(255),
  challenge_publisher_id        bigint,
  number_of_applicants          integer not null,
  constraint pk_challenge primary key (id)
);

create table challenge_application (
  id                            bigint auto_increment not null,
  challenge_id                  bigint,
  applicant_id                  bigint,
  apply_headline                varchar(255),
  apply_cover_letter            varchar(255),
  apply_description             varchar(255),
  rating                        double not null,
  rating_count                  bigint not null,
  recommend_rating              double not null,
  recommend_rating_count        bigint not null,
  homepage                      varchar(255),
  avatar                        varchar(255),
  created_time                  varchar(255),
  status                        varchar(255),
  is_active                     varchar(255),
  constraint pk_challenge_application primary key (id)
);

create table course (
  id                            bigint auto_increment not null,
  is_active                     tinyint(1) default 0 not null,
  course_id                     varchar(255),
  name                          varchar(255),
  description                   varchar(255),
  prerequisite                  varchar(255),
  start_semester                varchar(255),
  start_year                    varchar(255),
  end_semester                  varchar(255),
  end_year                      varchar(255),
  constraint pk_course primary key (id)
);

create table course_taassignment (
  id                            bigint auto_increment not null,
  course_id                     bigint,
  ta_id                         bigint,
  semester                      varchar(255),
  year                          varchar(255),
  approved_hours                integer not null,
  f1approved                    varchar(255),
  constraint pk_course_taassignment primary key (id)
);

create table file (
  id                            bigint auto_increment not null,
  file_name                     varchar(255),
  path                          varchar(255),
  table_name                    varchar(255),
  file_type                     varchar(255),
  table_recorder_id             varchar(255),
  timestamp                     datetime,
  constraint pk_file primary key (id)
);

create table job (
  id                            bigint auto_increment not null,
  is_active                     varchar(255),
  pdf                           varchar(255),
  jobtxt                        varchar(255),
  status                        varchar(255),
  title                         varchar(255),
  goals                         varchar(255),
  min_salary                    integer not null,
  max_salary                    integer not null,
  short_description             varchar(255),
  long_description              varchar(255),
  fields                        varchar(255),
  publish_date                  varchar(255),
  publish_year                  varchar(255),
  publish_month                 varchar(255),
  image_url                     varchar(255),
  url                           varchar(255),
  organization                  varchar(255),
  location                      varchar(255),
  required_expertise            varchar(255),
  preferred_expertise           varchar(255),
  number_of_positions           varchar(255),
  expected_start_date           varchar(255),
  job_publisher_id              bigint,
  contact_person_name           varchar(255),
  contact_person_email          varchar(255),
  contact_person_phone          varchar(255),
  salary_low                    varchar(255),
  salary_high                   varchar(255),
  minimum_degree                varchar(255),
  minimum_degree_in_fields      varchar(255),
  type                          varchar(255),
  number_of_applicants          integer not null,
  update_time                   varchar(255),
  constraint pk_job primary key (id)
);

create table job_application (
  id                            bigint auto_increment not null,
  job_id                        bigint,
  applicant_id                  bigint,
  apply_headline                varchar(255),
  apply_cover_letter            varchar(255),
  referee1title                 varchar(255),
  referee1last_name             varchar(255),
  referee1first_name            varchar(255),
  referee1email                 varchar(255),
  referee1phone                 varchar(255),
  referee2title                 varchar(255),
  referee2last_name             varchar(255),
  referee2first_name            varchar(255),
  referee2email                 varchar(255),
  referee2phone                 varchar(255),
  referee3title                 varchar(255),
  referee3last_name             varchar(255),
  referee3first_name            varchar(255),
  referee3email                 varchar(255),
  referee3phone                 varchar(255),
  rating                        double not null,
  rating_count                  bigint not null,
  recommend_rating              double not null,
  recommend_rating_count        bigint not null,
  homepage                      varchar(255),
  avatar                        varchar(255),
  created_time                  varchar(255),
  is_active                     varchar(255),
  constraint pk_job_application primary key (id)
);

create table mail (
  id                            bigint auto_increment not null,
  title                         varchar(255),
  content                       varchar(255),
  timestamp                     datetime,
  sender_id                     bigint,
  receiver_id                   bigint,
  constraint pk_mail primary key (id)
);

create table mail_file (
  mail_id                       bigint not null,
  file_id                       bigint not null,
  constraint pk_mail_file primary key (mail_id,file_id)
);

create table operation_log (
  id                            bigint auto_increment not null,
  logger                        varchar(255),
  log_key                       varchar(255),
  channel                       varchar(255),
  user_id                       bigint,
  date_time                     varchar(255),
  timestamp                     bigint,
  ip                            varchar(255),
  route_pattern                 varchar(255),
  action_method                 varchar(255),
  controller                    varchar(255),
  comment                       varchar(255),
  constraint pk_operation_log primary key (id)
);

create table organization (
  id                            bigint auto_increment not null,
  organization_name             varchar(255),
  address                       varchar(255),
  focuses                       LONGTEXT,
  url                           varchar(255),
  organization_logo             varchar(255),
  number_of_employees           integer not null,
  organization_history          varchar(255),
  street_address1               varchar(255),
  street_address2               varchar(255),
  city                          varchar(255),
  state                         varchar(255),
  zip_code                      integer not null,
  short_description             LONGTEXT,
  long_description              LONGTEXT,
  fields                        LONGTEXT,
  contact_person_name           varchar(255),
  contact_person_email          varchar(255),
  contact_person_phone          bigint,
  registrar_id                  bigint,
  constraint pk_organization primary key (id)
);

create table user_organization (
  organization_id               bigint not null,
  user_id                       bigint not null,
  constraint pk_user_organization primary key (organization_id,user_id)
);

create table paper (
  id                            bigint auto_increment not null,
  title                         varchar(255),
  book_title                    varchar(255),
  editor                        varchar(255),
  abstract_text                 varchar(255),
  publication_type              varchar(255),
  publication_channel           varchar(255),
  date                          varchar(255),
  year                          varchar(255),
  month                         varchar(255),
  url                           varchar(255),
  publisher                     varchar(255),
  address                       varchar(255),
  isbn                          varchar(255),
  series                        varchar(255),
  school                        varchar(255),
  chapter                       varchar(255),
  volume                        varchar(255),
  number                        varchar(255),
  pages                         varchar(255),
  all_authors_string            varchar(255),
  constraint pk_paper primary key (id)
);

create table project (
  id                            bigint auto_increment not null,
  is_active                     varchar(255),
  parent_project_id             bigint,
  is_popular                    tinyint(1) default 0 not null,
  popular_ranking               bigint not null,
  authentication                varchar(255),
  access_times                  bigint not null,
  next_image_index              integer,
  title                         varchar(255),
  technology                    varchar(255),
  pdf                           varchar(255),
  image_url                     varchar(255),
  goals                         varchar(255),
  video_url                     varchar(255),
  github_url                    varchar(255),
  team_page_url                 varchar(255),
  location                      varchar(255),
  description                   varchar(255),
  start_date                    varchar(255),
  end_date                      varchar(255),
  principal_investigator_id     bigint,
  sponsor_contact_id            bigint,
  principal_investigator_organization_id bigint,
  sponsor_organization_id       bigint,
  constraint pk_project primary key (id)
);

create table rajob (
  id                            bigint auto_increment not null,
  is_active                     varchar(255),
  pdf                           varchar(255),
  status                        varchar(255),
  title                         varchar(255),
  goals                         varchar(255),
  min_salary                    integer not null,
  max_salary                    integer not null,
  ra_types                      integer not null,
  short_description             varchar(255),
  long_description              varchar(255),
  fields                        varchar(255),
  publish_date                  varchar(255),
  publish_year                  varchar(255),
  publish_month                 varchar(255),
  image_url                     varchar(255),
  url                           varchar(255),
  organization                  varchar(255),
  location                      varchar(255),
  required_expertise            varchar(255),
  preferred_expertise           varchar(255),
  number_of_positions           varchar(255),
  expected_start_date           varchar(255),
  expected_time_duration        varchar(255),
  rajob_publisher_id            bigint,
  number_of_applicants          integer not null,
  create_time                   varchar(255),
  update_time                   varchar(255),
  constraint pk_rajob primary key (id)
);

create table rajob_application (
  id                            bigint auto_increment not null,
  rajob_id                      bigint,
  applicant_id                  bigint,
  apply_headline                varchar(255),
  apply_cover_letter            varchar(255),
  referee1title                 varchar(255),
  referee1last_name             varchar(255),
  referee1first_name            varchar(255),
  referee1email                 varchar(255),
  referee1phone                 varchar(255),
  referee2title                 varchar(255),
  referee2last_name             varchar(255),
  referee2first_name            varchar(255),
  referee2email                 varchar(255),
  referee2phone                 varchar(255),
  referee3title                 varchar(255),
  referee3last_name             varchar(255),
  referee3first_name            varchar(255),
  referee3email                 varchar(255),
  referee3phone                 varchar(255),
  rating                        double not null,
  rating_count                  bigint not null,
  recommend_rating              double not null,
  recommend_rating_count        bigint not null,
  homepage                      varchar(255),
  avatar                        varchar(255),
  created_time                  varchar(255),
  is_active                     varchar(255),
  status                        varchar(255),
  constraint pk_rajob_application primary key (id)
);

create table researcher_info (
  highest_degree                varchar(255),
  orcid                         varchar(255),
  research_fields               varchar(255),
  school                        varchar(255),
  department                    varchar(255),
  user_id                       bigint,
  constraint uq_researcher_info_user_id unique (user_id)
);

create table reviewer (
  id                            bigint auto_increment not null,
  reviewer_name                 varchar(255),
  password                      varchar(255),
  first_name                    varchar(255),
  last_name                     varchar(255),
  middle_initial                varchar(255),
  affiliation                   varchar(255),
  title                         varchar(255),
  email                         varchar(255),
  mailing_address               varchar(255),
  phone_number                  varchar(255),
  research_fields               varchar(255),
  highest_degree                varchar(255),
  level                         varchar(255),
  homepage                      varchar(255),
  avatar                        varchar(255),
  rating                        double not null,
  rating_count                  bigint not null,
  recommend_rating              double not null,
  recommend_rating_count        bigint not null,
  create_time                   varchar(255),
  is_active                     varchar(255),
  constraint pk_reviewer primary key (id)
);

create table student_info (
  id_number                     varchar(255),
  student_year                  varchar(255),
  student_type                  varchar(255),
  major                         varchar(255),
  first_enroll_date             varchar(255),
  user_id                       bigint,
  constraint uq_student_info_user_id unique (user_id)
);

create table suggestion (
  id                            bigint auto_increment not null,
  title                         varchar(255),
  description                   varchar(255),
  solved                        integer not null,
  create_time                   datetime,
  solve_time                    datetime,
  long_description              varchar(255),
  reporter_id                   bigint,
  implementor_id                bigint,
  constraint pk_suggestion primary key (id)
);

create table tacandidate (
  id                            bigint auto_increment not null,
  is_active                     tinyint(1) default 0 not null,
  is_resume_sent                integer not null,
  smu_id                        varchar(255),
  semester                      varchar(255),
  year                          varchar(255),
  status                        varchar(255),
  hours                         integer not null,
  courses                       varchar(255),
  ta_applicant_id               bigint,
  preference                    varchar(255),
  unwanted                      varchar(255),
  comment                       varchar(255),
  constraint pk_tacandidate primary key (id)
);

create table tajob (
  id                            bigint auto_increment not null,
  is_active                     varchar(255),
  pdf                           varchar(255),
  status                        varchar(255),
  work_time                     integer not null,
  ta_job_semester_types         integer not null,
  ta_job_course_selections      integer not null,
  ta_courses_selection_hidden   varchar(255),
  title                         varchar(255),
  goals                         varchar(255),
  min_salary                    integer not null,
  max_salary                    integer not null,
  ta_types                      integer not null,
  short_description             varchar(255),
  long_description              varchar(255),
  fields                        varchar(255),
  publish_date                  varchar(255),
  publish_year                  varchar(255),
  publish_month                 varchar(255),
  image_url                     varchar(255),
  url                           varchar(255),
  organization                  varchar(255),
  location                      varchar(255),
  required_expertise            varchar(255),
  preferred_expertise           varchar(255),
  number_of_positions           varchar(255),
  expected_start_date           varchar(255),
  expected_time_dutation        varchar(255),
  tajob_publisher_id            bigint,
  number_of_applicants          integer not null,
  constraint pk_tajob primary key (id)
);

create table tajob_application (
  id                            bigint auto_increment not null,
  tajob_id                      bigint,
  applicant_id                  bigint,
  apply_date                    varchar(255),
  smu_id                        integer not null,
  ta_semester_types             integer not null,
  ta_student_types              integer not null,
  ta_student_admission_types    integer not null,
  ta_uscitizen                  integer not null,
  ta_native_language            integer not null,
  ta_english_proficiency_test   integer not null,
  ta_english_proficiency_test_name varchar(255),
  ta_english_proficiency_test_score double not null,
  ta_english_proficiency_test_date varchar(255),
  gre_v                         double not null,
  gre_q                         double not null,
  gre_a                         double not null,
  gre_date                      varchar(255),
  undergraduate_gpa             double not null,
  undergraduate_school          varchar(255),
  graduate_gpa                  double not null,
  graduate_school               varchar(255),
  class_rank_no_gpa             integer not null,
  score_percentage_no_gpa       double not null,
  grade_no_gpa                  double not null,
  other_info_no_gpa             varchar(255),
  enrolled_degree               integer not null,
  enrolled_phd_degree           integer not null,
  areas_research_interest1      varchar(255),
  areas_research_interest2      varchar(255),
  areas_research_interest3      varchar(255),
  areas_research_interest4      varchar(255),
  ra_smu                        integer not null,
  ra_smutime                    varchar(255),
  ra_smuadvisor_name            varchar(255),
  ra_smuadvisor_email           varchar(255),
  ta_smu                        integer not null,
  ta_smutime                    varchar(255),
  ta_smuadvisor_name            varchar(255),
  ta_smuadvisor_email           varchar(255),
  programming_language_cpp      integer not null,
  programming_language_java     integer not null,
  programming_language_python   integer not null,
  programming_language_r        integer not null,
  programming_language_sql      integer not null,
  programming_language_javascript integer not null,
  programming_language_verilog  integer not null,
  programming_language_assembler integer not null,
  programming_language_assembler_type varchar(255),
  computer_systems_type         varchar(255),
  ta_courses_preference         varchar(255),
  ta_courses_preference_hidden  varchar(255),
  ta_courses_not_preference     varchar(255),
  ta_courses_not_preference_hidden varchar(255),
  previous_teaching_exp1title   varchar(255),
  previous_teaching_exp1where   varchar(255),
  previous_teaching_exp1date    varchar(255),
  previous_teaching_exp2title   varchar(255),
  previous_teaching_exp2where   varchar(255),
  previous_teaching_exp2date    varchar(255),
  previous_teaching_exp3title   varchar(255),
  previous_teaching_exp3where   varchar(255),
  previous_teaching_exp3date    varchar(255),
  apply_headline                varchar(255),
  apply_cover_letter            varchar(255),
  tating                        double not null,
  tating_count                  bigint not null,
  recommend_rating              double not null,
  recommend_rating_count        bigint not null,
  homepage                      varchar(255),
  avatar                        varchar(255),
  created_time                  varchar(255),
  is_active                     varchar(255),
  constraint pk_tajob_application primary key (id)
);

create table taweekly_hours (
  id                            bigint auto_increment not null,
  week                          integer not null,
  hours                         integer not null,
  approval                      tinyint(1) default 0 not null,
  assignment_id                 bigint,
  constraint pk_taweekly_hours primary key (id)
);

create table technology (
  id                            bigint auto_increment not null,
  technology_title              varchar(255),
  goals                         varchar(255),
  short_description             varchar(255),
  long_description              varchar(255),
  keywords                      varchar(255),
  p_iname                       varchar(255),
  team_members                  varchar(255),
  fields                        varchar(255),
  organizations                 varchar(255),
  pdf                           varchar(255),
  representative_paper_url      varchar(255),
  rating                        double not null,
  rating_count                  bigint not null,
  recommend_rating              double not null,
  recommend_rating_count        bigint not null,
  homepage                      varchar(255),
  registered_time               varchar(255),
  is_active                     varchar(255),
  technology_publisher_id       bigint,
  constraint pk_technology primary key (id)
);

create table technology_usedin_project (
  technology_id                 bigint not null,
  project_id                    bigint not null,
  constraint pk_technology_usedin_project primary key (technology_id,project_id)
);

create table user (
  id                            bigint auto_increment not null,
  user_name                     varchar(255),
  password                      varchar(255),
  first_name                    varchar(255),
  last_name                     varchar(255),
  middle_initial                varchar(255),
  organization                  varchar(255),
  email                         varchar(255),
  mailing_address               varchar(255),
  phone_number                  varchar(255),
  level                         varchar(255),
  rating                        double not null,
  rating_count                  bigint not null,
  recommend_rating              double not null,
  recommend_rating_count        bigint not null,
  homepage                      varchar(255),
  avatar                        varchar(255),
  service_provider              tinyint(1) default 0 not null,
  expertises                    varchar(255),
  categories                    varchar(255),
  detail                        varchar(255),
  user_type                     integer,
  service_execution_counts      bigint not null,
  service_user                  tinyint(1) default 0 not null,
  create_time                   varchar(255),
  is_active                     varchar(255),
  token                         varchar(255),
  project_zone_id               bigint,
  unread_mention                tinyint(1) default 0 not null,
  constraint pk_user primary key (id)
);

create table user_participation_project (
  user_id                       bigint not null,
  project_id                    bigint not null,
  constraint pk_user_participation_project primary key (user_id,project_id)
);

create table followers (
  userid                        bigint not null,
  followerid                    bigint not null,
  constraint pk_followers primary key (userid,followerid)
);

create table friendrequests (
  userid                        bigint not null,
  senderid                      bigint not null,
  constraint pk_friendrequests primary key (userid,senderid)
);

create table friendship (
  useraid                       bigint not null,
  userbid                       bigint not null,
  constraint pk_friendship primary key (useraid,userbid)
);

alter table bug_report add constraint fk_bug_report_reporter_id foreign key (reporter_id) references user (id) on delete restrict on update restrict;
create index ix_bug_report_reporter_id on bug_report (reporter_id);

alter table bug_report add constraint fk_bug_report_fixer_id foreign key (fixer_id) references user (id) on delete restrict on update restrict;
create index ix_bug_report_fixer_id on bug_report (fixer_id);

alter table challenge add constraint fk_challenge_challenge_publisher_id foreign key (challenge_publisher_id) references user (id) on delete restrict on update restrict;
create index ix_challenge_challenge_publisher_id on challenge (challenge_publisher_id);

alter table challenge_application add constraint fk_challenge_application_challenge_id foreign key (challenge_id) references challenge (id) on delete restrict on update restrict;
create index ix_challenge_application_challenge_id on challenge_application (challenge_id);

alter table challenge_application add constraint fk_challenge_application_applicant_id foreign key (applicant_id) references user (id) on delete restrict on update restrict;
create index ix_challenge_application_applicant_id on challenge_application (applicant_id);

alter table course_taassignment add constraint fk_course_taassignment_course_id foreign key (course_id) references course (id) on delete restrict on update restrict;
create index ix_course_taassignment_course_id on course_taassignment (course_id);

alter table course_taassignment add constraint fk_course_taassignment_ta_id foreign key (ta_id) references tacandidate (id) on delete restrict on update restrict;
create index ix_course_taassignment_ta_id on course_taassignment (ta_id);

alter table job add constraint fk_job_job_publisher_id foreign key (job_publisher_id) references user (id) on delete restrict on update restrict;
create index ix_job_job_publisher_id on job (job_publisher_id);

alter table job_application add constraint fk_job_application_job_id foreign key (job_id) references job (id) on delete restrict on update restrict;
create index ix_job_application_job_id on job_application (job_id);

alter table job_application add constraint fk_job_application_applicant_id foreign key (applicant_id) references user (id) on delete restrict on update restrict;
create index ix_job_application_applicant_id on job_application (applicant_id);

alter table mail add constraint fk_mail_sender_id foreign key (sender_id) references user (id) on delete restrict on update restrict;
create index ix_mail_sender_id on mail (sender_id);

alter table mail add constraint fk_mail_receiver_id foreign key (receiver_id) references user (id) on delete restrict on update restrict;
create index ix_mail_receiver_id on mail (receiver_id);

alter table mail_file add constraint fk_mail_file_mail foreign key (mail_id) references mail (id) on delete restrict on update restrict;
create index ix_mail_file_mail on mail_file (mail_id);

alter table mail_file add constraint fk_mail_file_file foreign key (file_id) references file (id) on delete restrict on update restrict;
create index ix_mail_file_file on mail_file (file_id);

alter table user_organization add constraint fk_user_organization_organization foreign key (organization_id) references organization (id) on delete restrict on update restrict;
create index ix_user_organization_organization on user_organization (organization_id);

alter table user_organization add constraint fk_user_organization_user foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_user_organization_user on user_organization (user_id);

alter table project add constraint fk_project_principal_investigator_id foreign key (principal_investigator_id) references user (id) on delete restrict on update restrict;
create index ix_project_principal_investigator_id on project (principal_investigator_id);

alter table project add constraint fk_project_sponsor_contact_id foreign key (sponsor_contact_id) references user (id) on delete restrict on update restrict;
create index ix_project_sponsor_contact_id on project (sponsor_contact_id);

alter table project add constraint fk_project_principal_investigator_organization_id foreign key (principal_investigator_organization_id) references organization (id) on delete restrict on update restrict;
create index ix_project_principal_investigator_organization_id on project (principal_investigator_organization_id);

alter table project add constraint fk_project_sponsor_organization_id foreign key (sponsor_organization_id) references organization (id) on delete restrict on update restrict;
create index ix_project_sponsor_organization_id on project (sponsor_organization_id);

alter table rajob add constraint fk_rajob_rajob_publisher_id foreign key (rajob_publisher_id) references user (id) on delete restrict on update restrict;
create index ix_rajob_rajob_publisher_id on rajob (rajob_publisher_id);

alter table rajob_application add constraint fk_rajob_application_rajob_id foreign key (rajob_id) references rajob (id) on delete restrict on update restrict;
create index ix_rajob_application_rajob_id on rajob_application (rajob_id);

alter table rajob_application add constraint fk_rajob_application_applicant_id foreign key (applicant_id) references user (id) on delete restrict on update restrict;
create index ix_rajob_application_applicant_id on rajob_application (applicant_id);

alter table researcher_info add constraint fk_researcher_info_user_id foreign key (user_id) references user (id) on delete restrict on update restrict;

alter table student_info add constraint fk_student_info_user_id foreign key (user_id) references user (id) on delete restrict on update restrict;

alter table suggestion add constraint fk_suggestion_reporter_id foreign key (reporter_id) references user (id) on delete restrict on update restrict;
create index ix_suggestion_reporter_id on suggestion (reporter_id);

alter table suggestion add constraint fk_suggestion_implementor_id foreign key (implementor_id) references user (id) on delete restrict on update restrict;
create index ix_suggestion_implementor_id on suggestion (implementor_id);

alter table tacandidate add constraint fk_tacandidate_ta_applicant_id foreign key (ta_applicant_id) references user (id) on delete restrict on update restrict;
create index ix_tacandidate_ta_applicant_id on tacandidate (ta_applicant_id);

alter table tajob add constraint fk_tajob_tajob_publisher_id foreign key (tajob_publisher_id) references user (id) on delete restrict on update restrict;
create index ix_tajob_tajob_publisher_id on tajob (tajob_publisher_id);

alter table tajob_application add constraint fk_tajob_application_tajob_id foreign key (tajob_id) references tajob (id) on delete restrict on update restrict;
create index ix_tajob_application_tajob_id on tajob_application (tajob_id);

alter table tajob_application add constraint fk_tajob_application_applicant_id foreign key (applicant_id) references user (id) on delete restrict on update restrict;
create index ix_tajob_application_applicant_id on tajob_application (applicant_id);

alter table taweekly_hours add constraint fk_taweekly_hours_assignment_id foreign key (assignment_id) references course_taassignment (id) on delete restrict on update restrict;
create index ix_taweekly_hours_assignment_id on taweekly_hours (assignment_id);

alter table technology add constraint fk_technology_technology_publisher_id foreign key (technology_publisher_id) references user (id) on delete restrict on update restrict;
create index ix_technology_technology_publisher_id on technology (technology_publisher_id);

alter table technology_usedin_project add constraint fk_technology_usedin_project_technology foreign key (technology_id) references technology (id) on delete restrict on update restrict;
create index ix_technology_usedin_project_technology on technology_usedin_project (technology_id);

alter table technology_usedin_project add constraint fk_technology_usedin_project_project foreign key (project_id) references project (id) on delete restrict on update restrict;
create index ix_technology_usedin_project_project on technology_usedin_project (project_id);

alter table user add constraint fk_user_project_zone_id foreign key (project_zone_id) references project (id) on delete restrict on update restrict;
create index ix_user_project_zone_id on user (project_zone_id);

alter table user_participation_project add constraint fk_user_participation_project_user foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_user_participation_project_user on user_participation_project (user_id);

alter table user_participation_project add constraint fk_user_participation_project_project foreign key (project_id) references project (id) on delete restrict on update restrict;
create index ix_user_participation_project_project on user_participation_project (project_id);

alter table followers add constraint fk_followers_user_1 foreign key (userid) references user (id) on delete restrict on update restrict;
create index ix_followers_user_1 on followers (userid);

alter table followers add constraint fk_followers_user_2 foreign key (followerid) references user (id) on delete restrict on update restrict;
create index ix_followers_user_2 on followers (followerid);

alter table friendrequests add constraint fk_friendrequests_user_1 foreign key (userid) references user (id) on delete restrict on update restrict;
create index ix_friendrequests_user_1 on friendrequests (userid);

alter table friendrequests add constraint fk_friendrequests_user_2 foreign key (senderid) references user (id) on delete restrict on update restrict;
create index ix_friendrequests_user_2 on friendrequests (senderid);

alter table friendship add constraint fk_friendship_user_1 foreign key (useraid) references user (id) on delete restrict on update restrict;
create index ix_friendship_user_1 on friendship (useraid);

alter table friendship add constraint fk_friendship_user_2 foreign key (userbid) references user (id) on delete restrict on update restrict;
create index ix_friendship_user_2 on friendship (userbid);


# --- !Downs

alter table bug_report drop foreign key fk_bug_report_reporter_id;
drop index ix_bug_report_reporter_id on bug_report;

alter table bug_report drop foreign key fk_bug_report_fixer_id;
drop index ix_bug_report_fixer_id on bug_report;

alter table challenge drop foreign key fk_challenge_challenge_publisher_id;
drop index ix_challenge_challenge_publisher_id on challenge;

alter table challenge_application drop foreign key fk_challenge_application_challenge_id;
drop index ix_challenge_application_challenge_id on challenge_application;

alter table challenge_application drop foreign key fk_challenge_application_applicant_id;
drop index ix_challenge_application_applicant_id on challenge_application;

alter table course_taassignment drop foreign key fk_course_taassignment_course_id;
drop index ix_course_taassignment_course_id on course_taassignment;

alter table course_taassignment drop foreign key fk_course_taassignment_ta_id;
drop index ix_course_taassignment_ta_id on course_taassignment;

alter table job drop foreign key fk_job_job_publisher_id;
drop index ix_job_job_publisher_id on job;

alter table job_application drop foreign key fk_job_application_job_id;
drop index ix_job_application_job_id on job_application;

alter table job_application drop foreign key fk_job_application_applicant_id;
drop index ix_job_application_applicant_id on job_application;

alter table mail drop foreign key fk_mail_sender_id;
drop index ix_mail_sender_id on mail;

alter table mail drop foreign key fk_mail_receiver_id;
drop index ix_mail_receiver_id on mail;

alter table mail_file drop foreign key fk_mail_file_mail;
drop index ix_mail_file_mail on mail_file;

alter table mail_file drop foreign key fk_mail_file_file;
drop index ix_mail_file_file on mail_file;

alter table user_organization drop foreign key fk_user_organization_organization;
drop index ix_user_organization_organization on user_organization;

alter table user_organization drop foreign key fk_user_organization_user;
drop index ix_user_organization_user on user_organization;

alter table project drop foreign key fk_project_principal_investigator_id;
drop index ix_project_principal_investigator_id on project;

alter table project drop foreign key fk_project_sponsor_contact_id;
drop index ix_project_sponsor_contact_id on project;

alter table project drop foreign key fk_project_principal_investigator_organization_id;
drop index ix_project_principal_investigator_organization_id on project;

alter table project drop foreign key fk_project_sponsor_organization_id;
drop index ix_project_sponsor_organization_id on project;

alter table rajob drop foreign key fk_rajob_rajob_publisher_id;
drop index ix_rajob_rajob_publisher_id on rajob;

alter table rajob_application drop foreign key fk_rajob_application_rajob_id;
drop index ix_rajob_application_rajob_id on rajob_application;

alter table rajob_application drop foreign key fk_rajob_application_applicant_id;
drop index ix_rajob_application_applicant_id on rajob_application;

alter table researcher_info drop foreign key fk_researcher_info_user_id;

alter table student_info drop foreign key fk_student_info_user_id;

alter table suggestion drop foreign key fk_suggestion_reporter_id;
drop index ix_suggestion_reporter_id on suggestion;

alter table suggestion drop foreign key fk_suggestion_implementor_id;
drop index ix_suggestion_implementor_id on suggestion;

alter table tacandidate drop foreign key fk_tacandidate_ta_applicant_id;
drop index ix_tacandidate_ta_applicant_id on tacandidate;

alter table tajob drop foreign key fk_tajob_tajob_publisher_id;
drop index ix_tajob_tajob_publisher_id on tajob;

alter table tajob_application drop foreign key fk_tajob_application_tajob_id;
drop index ix_tajob_application_tajob_id on tajob_application;

alter table tajob_application drop foreign key fk_tajob_application_applicant_id;
drop index ix_tajob_application_applicant_id on tajob_application;

alter table taweekly_hours drop foreign key fk_taweekly_hours_assignment_id;
drop index ix_taweekly_hours_assignment_id on taweekly_hours;

alter table technology drop foreign key fk_technology_technology_publisher_id;
drop index ix_technology_technology_publisher_id on technology;

alter table technology_usedin_project drop foreign key fk_technology_usedin_project_technology;
drop index ix_technology_usedin_project_technology on technology_usedin_project;

alter table technology_usedin_project drop foreign key fk_technology_usedin_project_project;
drop index ix_technology_usedin_project_project on technology_usedin_project;

alter table user drop foreign key fk_user_project_zone_id;
drop index ix_user_project_zone_id on user;

alter table user_participation_project drop foreign key fk_user_participation_project_user;
drop index ix_user_participation_project_user on user_participation_project;

alter table user_participation_project drop foreign key fk_user_participation_project_project;
drop index ix_user_participation_project_project on user_participation_project;

alter table followers drop foreign key fk_followers_user_1;
drop index ix_followers_user_1 on followers;

alter table followers drop foreign key fk_followers_user_2;
drop index ix_followers_user_2 on followers;

alter table friendrequests drop foreign key fk_friendrequests_user_1;
drop index ix_friendrequests_user_1 on friendrequests;

alter table friendrequests drop foreign key fk_friendrequests_user_2;
drop index ix_friendrequests_user_2 on friendrequests;

alter table friendship drop foreign key fk_friendship_user_1;
drop index ix_friendship_user_1 on friendship;

alter table friendship drop foreign key fk_friendship_user_2;
drop index ix_friendship_user_2 on friendship;

drop table if exists author;

drop table if exists author_paper;

drop table if exists bug_report;

drop table if exists challenge;

drop table if exists challenge_application;

drop table if exists course;

drop table if exists course_taassignment;

drop table if exists file;

drop table if exists job;

drop table if exists job_application;

drop table if exists mail;

drop table if exists mail_file;

drop table if exists operation_log;

drop table if exists organization;

drop table if exists user_organization;

drop table if exists paper;

drop table if exists project;

drop table if exists rajob;

drop table if exists rajob_application;

drop table if exists researcher_info;

drop table if exists reviewer;

drop table if exists student_info;

drop table if exists suggestion;

drop table if exists tacandidate;

drop table if exists tajob;

drop table if exists tajob_application;

drop table if exists taweekly_hours;

drop table if exists technology;

drop table if exists technology_usedin_project;

drop table if exists user;

drop table if exists user_participation_project;

drop table if exists followers;

drop table if exists friendrequests;

drop table if exists friendship;

