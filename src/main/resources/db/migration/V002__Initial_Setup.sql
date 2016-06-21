create table patient (
  id varchar2(100),
  title varchar2(50),
  first_name varchar2(20),
  middle_names varchar2(200),
  surname varchar2(200),
  date_of_birth date,
  date_of_death date,
  nhs_number varchar2(200),
  national_insurance_number varchar2(200),
  primary key (id)
);

insert into patient (id, title, first_name, middle_names, surname) values ('6af283b0-d819-4cd0-8c04-50013b9f0906','Mr','Shaun','','Storey');
insert into patient (id, title, first_name, middle_names, surname) values ('2e025226-80af-4a65-9580-7e78c0379d19','Mrs','Shaun','','Storey');
insert into patient (id, title, first_name, middle_names, surname) values ('66d6b326-79ee-477b-92e5-3795cf8faab9','Miss','Lauren','','Storey');
insert into patient (id, title, first_name, middle_names, surname) values ('3296f7df-5ac3-49a3-b59b-91f1525c7ec9','Master','Daniel','Michael','Storey');
insert into patient (id, title, first_name, middle_names, surname) values ('5394fd28-302c-4348-8924-706bc2c6b914','Miss','Jessica','Annabel','Storey');

create table role (
  id varchar2(100),
  name varchar2(100),
  primary key (id)
);

insert into role (id, name) values ('dc08ff70-fa2c-40a6-aae6-b22a4edeeb0d','Staff Nurse');
insert into role (id, name) values ('99fc5523-6526-4830-9c91-e5663d43eafb','Sister');
insert into role (id, name) values ('d36019b4-e771-4451-a575-a52532f58a02','Health Care Assistant');
insert into role (id, name) values ('fb814475-89d6-40e2-adeb-909cb8b06517','Matron');
insert into role (id, name) values ('c5eb38c0-e324-40c6-ac1e-6a5faa513e84','Nurse Practitioner');
insert into role (id, name) values ('fb814475-e324-40c6-ac1e-e23c68aa7e89','Specialist Nurse');

insert into role (id, name) values ('4f9c3ed9-e6ca-4204-9435-b84cbdea1e78','Physician Associate');
insert into role (id, name) values ('9b84cbde-e6ca-4204-9435-a1e784f9c3ed','General Practitioner');
insert into role (id, name) values ('4443a59e-ab06-469f-ac98-e23c68aa7e89','Consultant');
insert into role (id, name) values ('b7c3780e-2db1-414c-b1e6-0d9d570c02cc','Registrar');
insert into role (id, name) values ('ff59f462-3b04-44fe-9222-b370ca71e996','Junior Doctor');

insert into role (id, name) values ('b370ca71-3b04-44fe-9222-e996ff59f462','Receptionist');

create table user (
  id varchar2(100),
  title varchar2(50),
  first_name varchar2(20),
  middle_names varchar2(200),
  surname varchar2(200),
  date_of_birth date,
  username varchar2(50),
  password varchar2(50),
  status varchar2(20),
  primary key (id)
);

insert into user (id, title, first_name, middle_names, surname, username, password, status)
values ('2e025226-80af-4a65-9580-7e78c0379d19','Mrs','Sharon','','Storey','bazstorey','letmein','ENABLED');

create table user_roles (user_id varchar2(100),
                         role varchar2(100),
                         primary key (user_id, role)
);

insert into user_roles (user_id, role) values ('2e025226-80af-4a65-9580-7e78c0379d19', 'Physician Associate');

create table organisation_type (
  id varchar2(100),
  type varchar2(200),
  primary key (id)
);

insert into organisation_type (id, type) values ('d52282ee-b7ab-428f-b724-df986a433bae','Hospital');
insert into organisation_type (id, type) values ('f15e3c8f-3beb-4223-a244-0bd8edb144a1','Physiotherapy Practice');
insert into organisation_type (id, type) values ('e9bcfcf3-f9fa-4308-bda9-562e3d1aefb7','GP Surgery');

create table organisation (
  id varchar2(100),
  name varchar2(200),
  description varchar2(500),
  website_url varchar2(500),
  type varchar2(100),
  primary key (id)
);

insert into organisation (id, name, type) values ('4f9c3ed9-e6ca-4204-9435-b84cbdea1e78','Stepping Hill Hospital','Hospital');
insert into organisation (id, name, type) values ('ff59f462-3b04-44fe-9222-b370ca71e996','Sally Scott Physio','Physiotherapy Practice');
insert into organisation (id, name, type) values ('9b84cbde-e6ca-4204-9435-a1e784f9c3ed','Manchester Royal Infirmary','Hospital');
insert into organisation (id, name, type) values ('b370ca71-3b04-44fe-9222-e996ff59f462','Salford Royal Infirmary','Hospital');

create table user_organisation_link (
  id varchar2(100),
  user_id varchar2(100),
  organisation_id varchar2(100),
  primary key (id)
);

create table address_type (
  id varchar2(100),
  type varchar2(200),
  primary key (id)
);

insert into address_type (id, type) values ('d52282ee-b7ab-428f-b724-df986a433bae','Home');
insert into address_type (id, type) values ('f15e3c8f-3beb-4223-a244-0bd8edb144a1','Word');
insert into address_type (id, type) values ('e9bcfcf3-f9fa-4308-bda9-562e3d1aefb7','Business');

create table address (
  id varchar2(100),
  patient_id varchar2(100),
  organisation_id varchar2(100),
  type varchar2(100),
  house_name_number varchar2(100),
  street varchar2(100),
  locality varchar2(100),
  town varchar2(100),
  county varchar2(100),
  country varchar2(100),
  postcode varchar2(10),
  primary key (id)
);

insert into address (id, patient_id, type, house_name_number, street, locality, town, county, postcode)
values ('b144a1f1-3beb-4223-a244-0bd8ed5e3c8f','2e025226-80af-4a65-9580-7e78c0379d19','Home','5','Langford Road','Heaton Chapel','Stockport','Cheshire','SK45BR');

create table clinic (
  id varchar2(100),
  organisation_id varchar2(100),
  user_id varchar2(100),
  name varchar2(100),
  start_time timestamp,
  end_time timestamp,
  appointment_duration integer,
  capactiy integer,
  primary key (id)
);

insert into clinic (id, organisation_id, user_id, name, start_time, end_time, appointment_duration, capactiy)
values ('0bd8a1f1-3beb-4223-a244-ed5e3c8fb144','4f9c3ed9-e6ca-4204-9435-b84cbdea1e78','2e025226-80af-4a65-9580-7e78c0379d19',
        'Sharons Astma Clinic AM','2016-05-20 09:00:00', '2016-05-20 12:00:00','15','1');
insert into clinic (id, organisation_id, user_id, name, start_time, end_time, appointment_duration, capactiy)
values ('a244a1f1-3beb-4223-0bd8-ed5e3c8fb144','4f9c3ed9-e6ca-4204-9435-b84cbdea1e78','2e025226-80af-4a65-9580-7e78c0379d19',
        'Sharons Diabetes Clinic AM','2016-05-20 13:00:00', '2016-05-20 17:00:00','15','1');

create table appointment (
  id varchar2(100),
  clinic_id varchar2(100),
  patient_id varchar2(100),
  start_time timestamp,
  end_time timestamp,
  primary key (id)
);

insert into appointment (id, clinic_id, patient_id, start_time, end_time)
values ('df986a43-b7ab-428f-b724-d52282ee3bae','0bd8a1f1-3beb-4223-a244-ed5e3c8fb144','66d6b326-79ee-477b-92e5-3795cf8faab9','2016-05-20 09:00:00', '2016-05-20 09:15:00');

insert into appointment (id, clinic_id, patient_id, start_time, end_time)
values ('b7ab6a43-df98-428f-b724-d52282ee3bae','a244a1f1-3beb-4223-0bd8-ed5e3c8fb144','6af283b0-d819-4cd0-8c04-50013b9f0906','2016-05-20 13:15:00', '2016-05-20 13:00:00');