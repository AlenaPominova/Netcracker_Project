WITH RECURSIVE b AS (
	SELECT	0 AS level,
		obj1.object_id, 
		obj1.parent_id, 
		obj1.name AS obj_name,
		obj1.object_type_id
	FROM "OBJECTS" obj1
	WHERE object_id = 100
	
	UNION
	
	SELECT  b.level - 1 as level, 
		obj2.object_id, 
		obj2.parent_id, 
		obj2.name AS obj_name, 
		obj2.object_type_id
	FROM "OBJECTS" obj2
	JOIN b
		ON obj2.object_id = b.parent_id
)

SELECT 	b.level,
	b.object_id,
	b.obj_name,
	b.parent_id,
	b.object_type_id,
	ot.name as obj_type_name,
	a.attr_id,
	a.name as attr_name,
	a.attr_type_id,
	at.name as attr_type_name,
	ref.reference,
	referenced_objects.referenced_object_name,
	CONCAT (p.value, p.date_value, referenced_objects.referenced_object_name, lv.value) as value,
	lv.list_value_id, 
	lv.value
	FROM b
	  JOIN "OBJECT_TYPES" OT USING (object_type_id)
	  JOIN "ATTR_OBJECT_TYPES" aot ON b.object_type_id = aot.object_type_id
	  JOIN "ATTRIBUTES" a ON a.attr_id = aot.attr_id
	  JOIN "ATTR_TYPES" AT USING (attr_type_id)
	  LEFT JOIN "REFERENCES" ref ON b.object_id = ref.object_id AND a.attr_id = ref.attr_id
	  LEFT JOIN (
		SELECT DISTINCT ref1.reference, obj.name as referenced_object_name
			FROM "REFERENCES" ref1
			JOIN "OBJECTS" obj ON ref1.reference = obj.object_id
		)  as referenced_objects
		ON ref.reference = referenced_objects.reference
	 LEFT JOIN "PARAMS" p ON a.attr_id = p.attr_id AND b.object_id = p.object_id
	LEFT JOIN "LIST_VALUES" lv USING (list_value_id)
	
ORDER BY b.level, b.object_id, a.attr_id
;

/*simple select*/
SELECT 	o.object_id,
	o.name,
	o.parent_id,
	o.object_type_id,
	ot.name as obj_type_name,
	a.attr_id,
	a.name as attr_name,
	a.attr_type_id,
	at.name as attr_type_name,
	ref.reference,
	referenced_objects.referenced_object_name,
	CONCAT (p.value, p.date_value, referenced_objects.referenced_object_name, lv.value) as value,
	lv.list_value_id, 
	lv.value as list_value
	FROM "OBJECTS" o
	  JOIN "OBJECT_TYPES" OT USING (object_type_id)
	  JOIN "ATTR_OBJECT_TYPES" aot ON o.object_type_id = aot.object_type_id
	  JOIN "ATTRIBUTES" a ON a.attr_id = aot.attr_id
	  JOIN "ATTR_TYPES" AT USING (attr_type_id)
	  LEFT JOIN "REFERENCES" ref ON o.object_id = ref.object_id AND a.attr_id = ref.attr_id
	  LEFT JOIN (
		SELECT DISTINCT ref1.reference, obj.name as referenced_object_name
			FROM "REFERENCES" ref1
			JOIN "OBJECTS" obj ON ref1.reference = obj.object_id
		)  as referenced_objects
		ON ref.reference = referenced_objects.reference
	 LEFT JOIN "PARAMS" p ON a.attr_id = p.attr_id AND o.object_id = p.object_id
	LEFT JOIN "LIST_VALUES" lv USING (list_value_id)
WHERE o.object_id = 4 and a.attr_id = 200
ORDER BY o.object_id, a.attr_id
;