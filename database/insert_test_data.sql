INSERT INTO public."OBJECTS"(
            object_id, object_type_id, name, description)
    VALUES 
			(5, 2, 'Иванов', NULL),
			(6, 2, 'Бабкина', NULL),
			(7, 2, 'Василий Васильевич', NULL),			
			(8, 2, 'Попова Юлия', NULL),	
			(9, 2, 'Ахмедов Андрей', NULL),	
			(10, 3, 'Parking1', NULL),	
			(11, 3, 'Parking2', NULL),	
			(13, 3, 'ParkingGallery', NULL),	
			(401, 2, 'Some RestAPI User', NULL)
;
	 
INSERT INTO public."REFERENCES"(
            attr_id, reference, object_id)
    VALUES 

		(200, 2, 5),
		(200, 2, 6),
		(200, 3, 7),
		(200, 3, 8),
		(200, 3, 9),
		(200, 4, 401),
		
		(300, 5, 10),
		(300, 5, 11),
		(300, 6, 13)	
;
		
INSERT INTO public."PARAMS"(
            attr_id, object_id, value, date_value, list_value_id)
    VALUES 
		(201, 5,'+780065423', null, null),
		(202, 5,'mail@gmail.com', null, null),
		(203, 5,'adfages7ScxQ', null, null),
		(101, 5,'Воронеж', null, null),
		(205, 5, null, '1981-11-23', null),
		(206, 5, 'ТРЦ Арена', null, null),
		(100, 5, '4.95', null, null),
		
		(201, 6,'+79501231233', null, null),
		(202, 6,'sok@yandex.ru', null, null),
		(203, 6,'qEsdqq123sda', null, null),
		(101, 6,'Moscow', null, null),
		(205, 6, null,'1995-01-10', null),
		(206, 6, 'Google',null, null),	
		(100, 6, '5', null, null),		
		
		(201, 7,'+79374221123', null, null),
		(202, 7,'babamanya66@mail.ru', null, null),
		(203, 7,'qasdzx3Z', null, null),
		(101, 7,'Earth', null, null),
		(205, 7, null,'1966-04-13', null),
		(100, 7, '5', null, null),	
		
		(201, 8,'353535', null, null),
		(202, 8,'vasvas10@mail.ru', null, null),
		(203, 8,'aqweadfdx3Z', null, null),
		(101, 8,'Воронеж', null, null),
		(205, 8, null,'2000-12-31', null),
		(100, 8, '5', null, null),	
		
		(201, 9,'123456789', null, null),
		(202, 9,'kiqodasad@mail.ru', null, null),
		(203, 9,'ZXQs3Z', null, null),
		(101, 9,'Воронеж', null, null),
		(205, 9, null,'1979-10-11', null),
		(100, 9, '5', null, null),		
			
		(301, 10, '51.710777',null, null),
		(302, 10, '39.161406',null, null),
		(101, 10, 'бул. Победы, 23Б',null, null),
		(304, 10, '200.00',null, null),
		(305, 10, null , to_timestamp('06:00', 'hh24:mi'), null),
		(306, 10, null , to_timestamp('22:00', 'hh24:mi'), null),		
		(307, 10, '120', null, null),
		(308, 10, null , null, 2),
		(100, 10, '4.6',null, null),
		
		(301, 11, '51.656724',null, null),
		(302, 11, '39.190038',null, null),
		(101, 11, 'ул. Кирова, 11',null, null),
		(304, 11, '250.00',null, null),
		(305, 11, null , to_timestamp('08:00', 'hh24:mi'), null),
		(306, 11, null , to_timestamp('17:00', 'hh24:mi'), null),			
		(307, 11, '1', null, null),
		(308, 11, null , null, 2),
		(100, 11, '5.0',null, null),
		
		(301, 13, '51.666499',null, null),
		(302, 13, '39.191790',null, null),
		(101, 13, 'Кольцовская улица, 35А',null, null),
		(304, 13, '40.00',null, null),
		(305, 13, null , to_timestamp('10:00', 'hh24:mi'), null),
		(306, 13, null , to_timestamp('21:30', 'hh24:mi'), null),			
		(307, 13, '900', null, null),
		(308, 13, null , null, 2),
		(100, 13, '5.0',null, null)
;