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
			(100, 2, 'Administrator', NULL),		
			(200, 2, 'Owner', NULL),		
			(300, 2, 'User', NULL),			
			(400, 2, 'ParkinGo frontend', NULL),
			(12, 3, 'Parking3', NULL)
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
	(309, 1, 'evac_order_id', null)
;
	 
INSERT INTO public."REFERENCES"(
            attr_id, reference, object_id)
    VALUES 
		(200, 1, 100),
		(200, 2, 200),
		(200, 3, 300),
		(200, 4, 400),
		(300, 200, 12)
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
		(200, 100, '4.99', null, null),
		(102, 100, 'http://www.our3dvr.com/data/wallpapers/148/WDF_1889937.png', null, null),		
		
		(201, 200,'+79501231212', null, null),
		(202, 200,'owner@gmail.com', null, null),
		(203, 200,'b3duZXJAZ21haWwuY29tqDGi2qUQCtNm370ysZqgy/xj8vEa+ENzjFZ+PI5o+qc=', null, null),
		(101, 200,'Воронеж', null, null),
		(205, 200, null, '1981-11-23', null),
		(200, 200, '3.45', null, null),
		
		(201, 300,'+96075615457', null, null),
		(202, 300,'user@gmail.com', null, null),
		(203, 300,'dXNlckBnbWFpbC5jb20/GaDlSu2bju1Pk5lMuCKUfxZnFu/VwyIxhejA3jtjtA==', null, null),
		(101, 300,'Воронеж', null, null),
		(205, 300, null, '1981-11-23', null),
		(200, 300, '4.0', null, null),
		
		(201, 400,'+79001234567', null, null),
		(202, 400,'rest@gmail.com', null, null),
		(203, 400,'cmVzdEBnbWFpbC5jb21tB9hVpucaKSiH/KIbrrqH7Ay0obL+KS42xpxd87TmFA==', null, null),
		(101, 400,'Воронеж', null, null),
		(205, 400, null, '1981-11-23', null),
		(206, 400,'ВГУ', null, null),
		
		(301, 12, '51.656991',null, null),
		(302, 12, '39.205927',null, null),
		(101, 12, 'ВГУ, Университетская пл., 1',null, null),
		(304, 12, '450.00',null, null),
		(305, 12, null , to_timestamp('08:00', 'hh24:mi'), null),
		(306, 12, null , to_timestamp('20:00', 'hh24:mi'), null),				
		(307, 12, '0', null, null),
		(308, 12, null , null, 1),
		(100, 12, '5.0',null, null)	,
		(102, 12, 'http://domovoi-znak.ru/components/com_jshopping/files/img_products/full_no_parking_1.png',null, null)		
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
			(309, 3, 0)
;

INSERT INTO public."LIST_VALUES"(
            list_value_id, attr_id, value)
    VALUES 
			(1, 308, 'Occupied'),
			(2, 308, 'Free')
;