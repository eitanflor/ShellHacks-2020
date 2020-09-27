set identity_insert customer on;
INSERT INTO customer (id,first_name,last_name,date_of_birth,sex,Address,contact_mobile,contact_email,date_registered) VALUES ('1','Jerry','Contreras','11/3/1999','M','6 Water St. Miami, FL 33173','7863167844','jerry.cont@gmail.com','5/11/2020');
INSERT INTO customer (id,first_name,last_name,date_of_birth,sex,Address,contact_mobile,contact_email,date_registered) VALUES ('2','Eitan','Flor','10/14/1999','M','53 Peninsula Drive Miami, FL 33186','7866134578','eflor@aol.com','8/19/2019');
INSERT INTO customer (id,first_name,last_name,date_of_birth,sex,Address,contact_mobile,contact_email,date_registered) VALUES ('3','Jarret ','Torres','7/12/2000','M','410 S. Williams Dr. Hialeah, FL 33015','7865487316','serrot12@yahoo.com','1/5/2020');
INSERT INTO customer (id,first_name,last_name,date_of_birth,sex,Address,contact_mobile,contact_email,date_registered) VALUES ('4','Hanson','Nguyen','8/23/2000','M','85 Sunbeam Court Fort Lauderdale, FL 33312','3053122981','winner20@hotmail.com','9/26/2020');
set identity_insert customer off;

INSERT INTO car (id,vin,license_plate,make,model,year,color,state,customer_id) VALUES ('1','JN1HS36P2LW140218','IHPH69','Nissan','240SX','1990','black','florida','1');
INSERT INTO car (id,vin,license_plate,make,model,year,color,state,customer_id) VALUES ('2','JTJZB1BA8A2400307','CB7R605','Lexus','RX 450h','2010','grey','texas','2');
INSERT INTO car (id,vin,license_plate,make,model,year,color,state,customer_id) VALUES ('3','1GNDS13S132266223','MIDLYPH','Chevrolet','TrailBlazer','2003','yellow','california','3');
INSERT INTO car (id,vin,license_plate,make,model,year,color,state,customer_id) VALUES ('4','JN1CV6EP0A0013908','NBYOND','Infinity ','G37','2010','grey','maryland','4');
INSERT INTO car (id,vin,license_plate,make,model,year,color,state,customer_id) VALUES ('5','5YJSA1AG1DFP11043','OPECLOL','Tesla','Model S','2013','White','Virginia','3');

set identity_insert service on;
INSERT INTO service (id,service_name,duration,price) VALUES ('1','car wash','60','150');
INSERT INTO service (id,service_name,duration,price) VALUES ('2','oil change','75','200');
INSERT INTO service (id,service_name,duration,price) VALUES ('3','repair','90','600');
INSERT INTO service (id,service_name,duration,price) VALUES ('4','inspection','100','325');
set identity_insert service off;

set identity_insert service_provided on;
INSERT INTO service_provided (id,appointment_id,service_id) VALUES ('1','1','4');
INSERT INTO service_provided (id,appointment_id,service_id) VALUES ('2','2','2');
INSERT INTO service_provided (id,appointment_id,service_id) VALUES ('3','3','1');
INSERT INTO service_provided (id,appointment_id,service_id) VALUES ('4','4','3');
set identity_insert service_provided off;

set identity_insert employee on;
INSERT INTO employee (id,first_name,last_name,employee_type) VALUES ('1','Jony','Cobo','Service Advisors');
INSERT INTO employee (id,first_name,last_name,employee_type) VALUES ('2','Jonathan','Solis','Service Advisors');
INSERT INTO employee (id,first_name,last_name,employee_type) VALUES ('3','Alejandro','Mejia','Front Desktop');
INSERT INTO employee (id,first_name,last_name,employee_type) VALUES ('4','Reiwan','Ali','Sales');
INSERT INTO employee (id,first_name,last_name,employee_type) VALUES ('5','Alton','Smith','Manager');
set identity_insert employee off;

set identity_insert appointment on;
INSERT INTO appointment (id,employee_created,car_id,customer_id,employee_id,start_time,end_time,price_expected,price_full,discount,price_final,canceled,cancellation_reason,notes) VALUES ('1','1','1','1','2','2020-5-29 17:54:45','2020-5-29 19:30:15','300','325','0.1','292.5','0',null,'car has high mileage; check upon next visit');
INSERT INTO appointment (id,employee_created,car_id,customer_id,employee_id,start_time,end_time,price_expected,price_full,discount,price_final,canceled,cancellation_reason,notes) VALUES ('2','3','1','1','2','6-25-2020 09:35:12','6-25-2020 10:20:28','200','200','0','200','0',null,NULL);
INSERT INTO appointment (id,employee_created,car_id,customer_id,employee_id,start_time,end_time,price_expected,price_full,discount,price_final,canceled,cancellation_reason,notes) VALUES ('3','5','3','3','1','07-12-2020 11:05:02','07-12-2020 13:03:58','150','150','0.5','75','0',null,NULL);
INSERT INTO appointment (id,employee_created,car_id,customer_id,employee_id,start_time,end_time,price_expected,price_full,discount,price_final,canceled,cancellation_reason,notes) VALUES ('4','4','4','4','5','07-18-2020 20:02:03','07-18-2020 21:31:11','600','600','0','600','0',null,NULL);
INSERT INTO appointment (id,employee_created,car_id,customer_id,employee_id,start_time,end_time,price_expected,price_full,discount,price_final,canceled,cancellation_reason,notes) VALUES ('5','5','2','2','2',NULL,NULL,NULL,NULL,NULL,NULL,'1','family emergency',NULL);
INSERT INTO appointment (id,employee_created,car_id,customer_id,employee_id,start_time,end_time,price_expected,price_full,discount,price_final,canceled,cancellation_reason,notes) VALUES ('6','2','5','3','1','09-26-2020 5:02:14',NULL,NULL,NULL,NULL,NULL,'0',NULL,NULL);
set identity_insert appointment off;