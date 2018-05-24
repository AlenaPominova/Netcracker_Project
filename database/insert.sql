INSERT INTO public."OBJECT_TYPES"(
            object_type_id, name, parent_id)
    VALUES 
			(1, 'Role', null), 
			(2, 'User', null), 
			(3, 'Parking', null)
;

INSERT INTO public."OBJECTS"(
            object_id, object_type_id, name, description)
    VALUES 
			(1, 1, 'Administrator', NULL),
			(2, 1, 'Owner', NULL),
			(3, 1, 'User', NULL),
			(4, 1, 'RestAPI User', NULL),			
			(100, 2, 'ParkinGo Administrator', NULL),		
			(200, 2, 'Owner', NULL),	
			(201, 2, 'Чижов Сергей Викторович', NULL),					
			(300, 2, 'User', NULL),				
			(400, 2, 'ParkinGo frontend', NULL),
			(401, 2, 'EvacService RestAPI user', NULL),
			
			(10, 3, 'ParkingGallery 547821457', NULL),
			(11, 3, 'Parking 2141764', NULL),
			(12, 3, 'ParkinGo_s Parking 00000', NULL)
			
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
		(201, 2, 'phone', null),
		(202, 2, 'email', null),
		(203, 2, 'password', null),	
		(205, 3, 'birthdate', null),
		(206, 3, 'organization_name', null),
		
		(100, 1, 'rating', null),
		(101, 2, 'address', null),
		(102, 2, 'img_url', null),
		
		(300, 0, 'owner_id', null),
		(301, 1, 'latitude', null),
		(302, 1, 'longitude', null),
		(304, 1, 'price', null),
		(305, 3, 'open_time', null),
		(306, 3, 'close_time', null),
		(307, 1, 'free_spots_count', null),
		(308, 10, 'status', null),	
		(309, 10, 'overtime_type', null),
		(310, 1, 'overtime_coeff', null),
		(330, 1, 'evac_order_id', null),
		(331, 2, 'evac_order_status', null)
;
	 
INSERT INTO public."REFERENCES"(
            attr_id, reference, object_id)
    VALUES 
		(200, 1, 100),
		(200, 2, 200),
		(200, 2, 201),
		(200, 3, 300),
		(200, 4, 400),
		(200, 4, 401),
		(300, 201, 10),
		(300, 200, 11),
		(300, 100, 12)
;
		
INSERT INTO public."PARAMS"(
            attr_id, object_id, value, date_value, list_value_id)
    VALUES 
		(201, 100,'+79005557799', null, null),
		(202, 100,'admin@gmail.com', null, null),
		(203, 100,'YWRtaW5AZ21haWwuY29tat8frbvenLJfyxcz/ooWpwgQ3+3bz2HTNVeqr7gDCKQ=', null, null),
		(101, 100,'Воронеж', null, null),
		(205, 100, null, '1981-11-23', null),
		(206, 100,'Netcracker', null, null),
		(100, 100, '4.99', null, null),
		(102, 100, 'https://cdn.onlinewebfonts.com/svg/img_506117.png', null, null),		
		
		(201, 200,'+79501231212', null, null),
		(202, 200,'owner@gmail.com', null, null),
		(203, 200,'b3duZXJAZ21haWwuY29tqDGi2qUQCtNm370ysZqgy/xj8vEa+ENzjFZ+PI5o+qc=', null, null),
		(101, 200,'улица Ворошилова, 6', null, null),
		(205, 200, null, '1981-11-23', null),
		(100, 200, '3.45', null, null),
		(102, 200, 'https://cdn.onlinewebfonts.com/svg/img_64159.png', null, null),

		(201, 201,'+7974213277', null, null),
		(202, 201,'chizg@gallery.com', null, null),
		(203, 201,'b3duZXJAZ21haWwuY29tqDGi2qUQCtNm370ysZqgy/xj8vEa+ENzjFZ+PI5o+qc=', null, null),
		(101, 201,'Воронеж', null, null),
		(205, 201, null, '1981-11-23', null),
		(206, 201,'Галерея Чижова', null, null),
		(100, 201, '4.2', null, null),
		(102, 201, 'https://yt3.ggpht.com/a-/AJLlDp1bYAvq-9ZskWLKD5ULEaPPqwtUQisEhxb8ZA=s900-mo-c-c0xffffffff-rj-k-no', null, null),			
		
		(201, 300,'+96075615457', null, null),
		(202, 300,'user@gmail.com', null, null),
		(203, 300,'dXNlckBnbWFpbC5jb20/GaDlSu2bju1Pk5lMuCKUfxZnFu/VwyIxhejA3jtjtA==', null, null),
		(101, 300,'Воронеж', null, null),
		(205, 300, null, '1981-11-23', null),
		(100, 300, '4.0', null, null),
		(102, 300, 'https://image.freepik.com/free-icon/no-translate-detected_318-9378.jpg', null, null),		
		
		(201, 400,'+79001234567', null, null),
		(202, 400,'rest@gmail.com', null, null),
		(203, 400,'cmVzdEBnbWFpbC5jb21tB9hVpucaKSiH/KIbrrqH7Ay0obL+KS42xpxd87TmFA==', null, null),
		(101, 400,'Воронеж', null, null),
		(205, 400, null, '1981-11-23', null),
		(206, 400,'ВГУ', null, null),
		
		(201, 401,'+79102234512', null, null),
		(202, 401,'evac-service@mail.ru', null, null),
		(203, 401,'ZXZhYy1zZXJ2aWNlQG1haWwuY29tDxzSbIu3Wir+XjvrtL1ItCWA3YXLrjchcY/z9XWG1JU=', null, null),
		(101, 401,'Воронеж', null, null),
		(205, 401, null, '2000-12-20', null),
		(206, 401,'ВГУ', null, null),
		
		(301, 10, '51.666499',null, null),
		(302, 10, '39.191790',null, null),
		(101, 10, 'Кольцовская улица, 35А',null, null),
		(304, 10, '40.00',null, null),
		(305, 10, null , to_timestamp('10:00', 'hh24:mi'), null),
		(306, 10, null , to_timestamp('21:30', 'hh24:mi'), null),			
		(307, 10, '900', null, null),
		(308, 10, null , null, 2),
		(100, 10, '5.0',null, null),
		(102, 10, 'https://i.ytimg.com/vi/hY-rgFq0VH0/maxresdefault.jpg',null, null),
		
		(301, 11, '51.657545',null, null),
		(302, 11, '39.182745',null, null),
		(101, 11, 'улица Ворошилова, 6',null, null),
		(304, 11, '75.00',null, null),
		(305, 11, null , to_timestamp('8:00', 'hh24:mi'), null),
		(306, 11, null , to_timestamp('18:00', 'hh24:mi'), null),			
		(307, 11, '1', null, null),
		(308, 11, null , null, 2),
		(100, 11, '2.0',null, null),
		(102, 11, 'https://s00.yaplakal.com/pics/pics_original/0/2/1/6836120.jpg',null, null),
		(309, 11, null, null, 3),
		
		(301, 12, '51.700701',null, null),
		(302, 12, '39.130788',null, null),
		(101, 12, 'ул. Антонова-Овсеенко, 16',null, null),
		(304, 12, '0.00',null, null),
		(305, 12, null , to_timestamp('0:00', 'hh24:mi'), null),
		(306, 12, null , to_timestamp('0:00', 'hh24:mi'), null),			
		(307, 12, '10', null, null),
		(308, 12, null , null, 2),
		(100, 12, '0.0',null, null),
		(102, 12, 'http://www.avtoindent.ru/_bl/1/27049288.jpeg',null, null)
;

INSERT INTO public."ATTR_OBJECT_TYPES"(
            attr_id, object_type_id, options)
    VALUES 
			(200, 2, 0),
			(201, 2, 0),
			(202, 2, 0),
			(203, 2, 0),
			(205, 2, 0),
			(206, 2, 0),
			
			(100, 2, 0),
			(100, 3, 0),
			(101, 2, 0),
			(101, 3, 0),
			(102, 2, 0),
			(102, 3, 0),
			
			(300, 3, 0),
			(301, 3, 0),
			(302, 3, 0),
			(304, 3, 0),
			(305, 3, 0),
			(306, 3, 0),
			(307, 3, 0),
			(308, 3, 0),
			(309, 3, 0),
			(310, 3, 0),
			(330, 3, 0),
			(331, 3, 0)
;

INSERT INTO public."LIST_VALUES"(
            list_value_id, attr_id, value)
    VALUES 
			(1, 308, 'Occupied'),
			(2, 308, 'Free'),
			(3, 309, 'Перемещение на другую платную парковку'),
			(4, 309, 'Повышенние стоимости аренды')
;