SELECT	O.object_id, 
	O.name,
	OT.name,
	A.name,
	coalesce(select_references.ref_obj, '') || coalesce(select_params.p_value, '') as value
  FROM objects O
  left join object_types OT on
   O.object_type_id = OT.object_type_id
  left join attr_object_types AOT on
   AOT.object_type_id = OT.object_type_id
  left join attributes A on
   A.attr_id = AOT.attr_id
  left join (
	select distinct A.attr_id as id,
	R.object_id as obj_id,
	O.name as ref_obj
	from attributes A
	left join _references R on
	A.attr_id = R.attr_id
	left join objects O on
	R.reference = O.object_id
	where R.attr_id is not null 
  ) as select_references on
   A.attr_id = select_references.id and O.object_id = select_references.obj_id
  left join (
	select distinct A.attr_id as id,
	A.name,
	P.value as p_value
	from attributes A
	left join params P on
	A.attr_id = P.attr_id
	where P.attr_id is not null and P.ibject_id = 7
  ) as select_params on
  A.attr_id = select_params.id
  where O.object_id = 7
;