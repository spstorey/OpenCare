create table organisation (
  organisation_id varchar2(100),
  name varchar2(200),
  description varchar2(500),
  website_url varchar2(500),
  type varchar2(100),
  created_date timestamp,
  modified_date timestamp,
  primary key (organisation_id)
);

insert into organisation (organisation_id, name, type) values ('4f9c3ed9-e6ca-4204-9435-b84cbdea1e78','Stepping Hill Hospital','Hospital');
insert into organisation (organisation_id, name, type) values ('ff59f462-3b04-44fe-9222-b370ca71e996','Sally Scott Physio','Physiotherapy Practice');
insert into organisation (organisation_id, name, type) values ('9b84cbde-e6ca-4204-9435-a1e784f9c3ed','Manchester Royal Infirmary','Hospital');
insert into organisation (organisation_id, name, type) values ('b370ca71-3b04-44fe-9222-e996ff59f462','Salford Royal Infirmary','Hospital');

create table staff (
  staff_id varchar2(100),
  title varchar2(50),
  first_name varchar2(20),
  middle_names varchar2(200),
  surname varchar2(200),
  date_of_birth date,
  username varchar2(50),
  password varchar2(50),
  role varchar2(200),
  status varchar2(20),
  created_date timestamp,
  modified_date timestamp,
  primary key (staff_id)
);

insert into staff (staff_id, title, first_name, middle_names, surname, username, password, role, status)
values ('2e025226-80af-4a65-9580-7e78c0379d19','Mrs','Sharon','','Storey','bazstorey','letmein','Physican Associate','ENABLED');

insert into staff (staff_id, title, first_name, middle_names, surname, username, password, role, status)
values ('5226-802e02af-379d19-7e78c04a65-9580','Mr','Richard','','Wickes','rwickes','letmein','Consultant','ENABLED');

insert into staff (staff_id, title, first_name, middle_names, surname, username, password, role, status)
values ('c0379d192e025226-80af-4a65-9580-7e78','Mrs','Elizabeth','','Cain','ecain','letmein','General Practitioner','ENABLED');

create table patient (
  patient_id varchar2(100),
  title varchar2(50),
  first_name varchar2(20),
  middle_names varchar2(200),
  surname varchar2(200),
  date_of_birth date,
  date_of_death date,
  nhs_number varchar2(200),
  national_insurance_number varchar2(200),
  house_name_number varchar2(100),
  street varchar2(100),
  locality varchar2(100),
  city varchar2(100),
  county varchar2(100),
  country varchar2(100),
  postcode varchar2(15),
  home_phone varchar2(15),
  work_phone varchar2(15),
  mobile_phone varchar2(15),
  email varchar2(15),
  next_of_kin_name varchar2(15),
  next_of_kin_phone varchar2(15),
  created_date timestamp,
  modified_date timestamp,
  primary key (patient_id)
);

insert into patient (patient_id, title, first_name, middle_names, surname) values ('6af283b0-d819-4cd0-8c04-50013b9f0906','Mr','Shaun','','Storey');
insert into patient (patient_id, title, first_name, middle_names, surname) values ('2e025226-80af-4a65-9580-7e78c0379d19','Mrs','Shaun','','Storey');
insert into patient (patient_id, title, first_name, middle_names, surname) values ('66d6b326-79ee-477b-92e5-3795cf8faab9','Miss','Lauren','','Storey');
insert into patient (patient_id, title, first_name, middle_names, surname) values ('3296f7df-5ac3-49a3-b59b-91f1525c7ec9','Master','Daniel','Michael','Storey');
insert into patient (patient_id, title, first_name, middle_names, surname) values ('5394fd28-302c-4348-8924-706bc2c6b914','Miss','Jessica','Annabel','Storey');

create table clinic (
  clinic_id varchar2(100),
  organisation_id varchar2(100),
  name varchar2(100),
  start_time timestamp,
  end_time timestamp,
  appointment_duration integer,
  capacity integer,
  status varchar2(20),
  created_date timestamp,
  modified_date timestamp,
  primary key (clinic_id)
);

insert into clinic (clinic_id, organisation_id, name, start_time, end_time, appointment_duration, capacity)
values ('0bd8a1f1-3beb-4223-a244-ed5e3c8fb144','4f9c3ed9-e6ca-4204-9435-b84cbdea1e78',
        'Sharons Astma Clinic AM','2016-05-20 09:00:00', '2016-05-20 12:00:00','15','1');

insert into clinic (clinic_id, organisation_id, name, start_time, end_time, appointment_duration, capacity)
values ('a244a1f1-3beb-4223-0bd8-ed5e3c8fb144','4f9c3ed9-e6ca-4204-9435-b84cbdea1e78',
        'Sharons Diabetes Clinic AM','2016-05-20 13:00:00', '2016-05-20 17:00:00','15','1');

create table appointment (
  appointment_id varchar2(100),
  clinic_id varchar2(100),
  patient_id varchar2(100),
  start_time timestamp,
  end_time timestamp,
  created_date timestamp,
  modified_date timestamp,
  primary key (appointment_id)
);

insert into appointment (appointment_id, clinic_id, patient_id, start_time, end_time)
values ('df986a43-b7ab-428f-b724-d52282ee3bae','0bd8a1f1-3beb-4223-a244-ed5e3c8fb144','66d6b326-79ee-477b-92e5-3795cf8faab9','2016-05-20 09:00:00', '2016-05-20 09:15:00');

insert into appointment (appointment_id, clinic_id, patient_id, start_time, end_time)
values ('b7ab6a43-df98-428f-b724-d52282ee3bae','a244a1f1-3beb-4223-0bd8-ed5e3c8fb144','6af283b0-d819-4cd0-8c04-50013b9f0906','2016-05-20 13:15:00', '2016-05-20 13:00:00');