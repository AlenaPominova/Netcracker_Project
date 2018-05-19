INSERT INTO public."OBJECTS"(
            object_id, object_type_id, name, description)
    VALUES 
			(205, 2, 'Не Иванов Дмитрий', NULL),
			(206, 2, 'Бабкина Мария', NULL),
			
			(305, 2, 'Попова Юлия', NULL),	
			(306, 2, 'Ахмедов Андрей', NULL),	
			
			(15, 3, 'Parking 15', NULL),	
			(16, 3, 'Parking 16', NULL),
			(17, 3, 'Parking 17', NULL)
;
	 
INSERT INTO public."REFERENCES"(
            attr_id, reference, object_id)
    VALUES 

		(200, 2, 205),
		(200, 2, 206),
		(200, 3, 305),
		(200, 3, 306),
		
		(300, 205, 15),
		(300, 206, 16),
		(300, 206, 17)	
;
		
INSERT INTO public."PARAMS"(
            attr_id, object_id, value, date_value, list_value_id)
    VALUES 
		(201, 205,'+780065423', null, null),
		(202, 205,'arena@gmail.com', null, null),
		(203, 205,'adfages7ScxQ', null, null),
		(101, 205,'Воронеж', null, null),
		(205, 205, null, '1981-11-23', null),
		(206, 205, 'ТРЦ Арена', null, null),
		(100, 205, '4.95', null, null),
		
		(201, 206,'+79374221123', null, null),
		(202, 206,'babamanya66@mail.ru', null, null),
		(203, 206,'qasdzx3Z', null, null),
		(101, 206,'Earth', null, null),
		(205, 206, null,'1966-04-13', null),
		(100, 206, '5', null, null),
		
		(201, 305,'+79501231233', null, null),
		(202, 305,'pop@yandex.ru', null, null),
		(203, 305,'qEsdqq123sda', null, null),
		(101, 305,'Moscow', null, null),
		(205, 305, null,'1995-01-10', null),
		(100, 305, '5', null, null),		
				
		(201, 306,'123456789', null, null),
		(202, 306,'qwerqodasad@mail.ru', null, null),
		(203, 306,'ZXQs3Z', null, null),
		(101, 306,'Воронеж', null, null),
		(205, 306, null,'1979-10-11', null),
		(100, 306, '5', null, null),	
					
		(301, 15, '51.710777',null, null),
		(302, 15, '39.161406',null, null),
		(101, 15, 'бул. Победы, 23Б',null, null),
		(304, 15, '25.00',null, null),
		(305, 15, null , to_timestamp('06:00', 'hh24:mi'), null),
		(306, 15, null , to_timestamp('22:00', 'hh24:mi'), null),		
		(307, 15, '120', null, null),
		(308, 15, null , null, 2),
		(100, 15, '4.6',null, null),
		(102, 15, 'http://photoshare.ru/data/9/9155/1/4lrtvl-o1u.jpg?3',null, null),
				
		(301, 16, '51.661612',null, null),
		(302, 16, '39.194093',null, null),
		(101, 16, 'ул. Куколкина, 5А',null, null),
		(304, 16, '150.00',null, null),
		(305, 16, null , to_timestamp('08:00', 'hh24:mi'), null),
		(306, 16, null , to_timestamp('17:00', 'hh24:mi'), null),			
		(307, 16, '1', null, null),
		(308, 16, null , null, 2),
		(100, 16, '5.0',null, null),
		(309, 16, null, null, 4),
		(310, 16, '2.5', null, null),
		
		(301, 17, '51.656991',null, null),
		(302, 17, '39.205927',null, null),
		(101, 17, 'ВГУ, Университетская пл., 1',null, null),
		(304, 17, '450.00',null, null),
		(305, 17, null , to_timestamp('08:00', 'hh24:mi'), null),
		(306, 17, null , to_timestamp('20:00', 'hh24:mi'), null),				
		(307, 17, '35', null, null),
		(308, 17, null , null, 2),
		(100, 17, '5.0',null, null),
		(102, 17, 'http://domovoi-znak.ru/components/com_jshopping/files/img_products/full_no_parking_1.png',null, null)		
;