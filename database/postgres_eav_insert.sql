INSERT INTO public."OBJECT_TYPES"(
            object_type_id, name)
    VALUES 
			(1, 'Role'), 
			(2, 'User'), 
			(3, 'Parking'), 
			(4, 'Parking_spot'),
			(100, 'test_obj_type')
;

INSERT INTO public."OBJECTS"(
            object_id, object_type_id, name, parent_id)
    VALUES 
			(1, 1, 'Administrator', NULL),
			(2, 1, 'Owner', NULL),
			(3, 1, 'User', NULL),
			(4, 2, 'User1', NULL),
			(5, 2, 'User2', NULL),
			(6, 2, 'User3', NULL),
			(7, 2, 'User4', NULL),	
			(8, 2, 'User5', NULL),	
			(9, 2, 'User6', NULL),	
			(10, 3, 'Parking1', NULL),	
			(11, 3, 'Parking2', NULL),	
			(12, 4, 'Parking_spot1', 10),	
			(13, 4, 'Parking_spot2', 10),	
			(14, 4, 'Parking_spot3', 11),	
			(15, 4, 'Parking_spot4', 11),
			(100, 100, 'test_obj', 12)
	;
	
INSERT INTO public."ATTR_TYPES"(
            attr_type_id, name)
    VALUES 
			(0, 'Reference'), 
			(1, 'Number'), 
			(2, 'Text'), 
			(3, 'Date'), 
			(10, 'List')
;
	
INSERT INTO public."ATTRIBUTES"(
            attr_id, attr_type_id, name, def_value)
    VALUES 
	(200, 0, 'role_id', null),
	(201, 2, 'fio', null),
	(202, 2, 'phone', null),
	(203, 2, 'email', null),
	(204, 2, 'password', null),
	(205, 2, 'address', null),
	(206, 3, 'birthdate', null),
	(301, 1, 'latitude', null),
	(302, 1, 'longitude', null),
	(303, 2, 'parking_address', null),
	(304, 1, 'price', null),
	(305, 3, 'open_time', null),
	(306, 3, 'close_time', null),
	(307, 0, 'owner_id', null),
	(402, 10, 'parking_spot_status', null),
	(403, 0, 'customer_id', null),
	(501, 2, 'test_attr_1', null),
	(502, 2, 'test_attr_2', null)
;
	 
INSERT INTO public."REFERENCES"(
            attr_id, reference, object_id)
    VALUES 
		(200, 1, 4),
		(200, 3, 5),
		(200, 3, 6),
		(200, 3, 7),
		(307, 8, 10),
		(307, 9, 11),
		(403, 4, 12),
		(403, 5, 13),
		(403, 6, 14)
;
		
INSERT INTO public."PARAMS"(
            attr_id, object_id, value, date_value, list_value_id)
    VALUES 
		(201, 4,'Иванов', null, null),
		(202, 4,'+780065423', null, null),
		(203, 4,'mail@gmail.com', null, null),
		(204, 4,'adfages7ScxQ', null, null),
		(205, 4,'Воронеж', null, null),
		(206, 4, null, '1981-11-23', null),
		(201, 5,'Соколов', null, null),
		(202, 5,'+79501231233', null, null),
		(203, 5,'sok@yandex.ru', null, null),
		(204, 5,'qEsdqq123sda', null, null),
		(205, 5,'Moscow', null, null),
		(206, 5, null,'1995-01-10', null),
		(201, 6,'Бабкина', null, null),
		(202, 6,'+79374221123', null, null),
		(203, 6,'babamanya66@mail.ru', null, null),
		(204, 6,'qasdzx3Z', null, null),
		(205, 6,'Earth', null, null),
		(206, 6, null,'1966-04-13', null),
		(201, 7,'Вася', null, null),
		(202, 7,'353535', null, null),
		(203, 7,'vasvas10@mail.ru', null, null),
		(204, 7,'aqweadfdx3Z', null, null),
		(205, 7,'Воронеж', null, null),
		(206, 7, null,'2000-12-31', null),
		(201, 8,'Попова Юлия', null, null),
		(202, 8,'123456789', null, null),
		(203, 8,'kiqodasad@mail.ru', null, null),
		(204, 8,'ZXQs3Z', null, null),
		(205, 8,'Воронеж', null, null),
		(206, 8, null,'1979-10-11', null),
		(201, 9,'Ахмедов Андрей', null, null),
		(202, 9,'3437119', null, null),
		(203, 9,'somemail@mail.ru', null, null),
		(204, 9,'1asd4304ZXQs3Z', null, null),
		(205, 9,'Воронеж', null, null),
		(206, 9, null,'1994-05-11', null),		
		(301, 10, '51.6754966',null, null),
		(302, 10, '39.2088823',null, null),
		(303, 10, 'пл. Ленина',null, null),
		(304, 10, '200.00',null, null),
		(305, 10, null , to_timestamp('08:00', 'hh24:mi'), null),
		(306, 10, null , to_timestamp('20:00', 'hh24:mi'), null),		
		(301, 11, '31.7551416',null, null),
		(302, 11, '49.1488153',null, null),
		(303, 11, 'ун. Кирова, 13',null, null),
		(304, 11, '250.00',null, null),
		(305, 11, null , to_timestamp('08:00', 'hh24:mi'), null),
		(306, 11, null , to_timestamp('18:00', 'hh24:mi'), null),
		(402, 12, null , null, 1),
		(402, 13, null , null, 1),
		(402, 14, null , null, 1),
		(402, 15, null , null, 2),
		(501, 100, 'test_value_1' , null, null),
		(502, 100, 'test_value_2' , null, null)
;

INSERT INTO public."ATTR_OBJECT_TYPES"(
            attr_id, object_type_id, options)
    VALUES 
			(200, 2, 0),
			(201, 2, 0),
			(202, 2, 0),
			(203, 2, 0),
			(204, 2, 0),
			(205, 2, 0),
			(206, 2, 0),
			(301, 3, 0),
			(302, 3, 0),
			(303, 3, 0),
			(304, 3, 0),
			(305, 3, 0),
			(306, 3, 0),
			(307, 3, 0),
			(402, 4, 0),
			(403, 4, 0),
			(501, 100, 0),
			(502, 100, 0)
;

INSERT INTO public."LIST_VALUES"(
            list_value_id, attr_id, value)
    VALUES 
			(1, 402, 'Occupied'),
			(2, 402, 'Free')
;