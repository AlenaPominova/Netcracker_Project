CREATE SEQUENCE public."parking_id_sequence"
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;
  
CREATE OR REPLACE FUNCTION public.generate_guid()
  RETURNS text AS
$BODY$
	BEGIN
		RETURN (to_char(current_timestamp, 'YYMMDDSSMS') || floor(random()*(1000)))::bigint;
	END;
$BODY$
  LANGUAGE plpgsql;

CREATE TABLE "OBJECTS" (
	"object_id" bigint NOT NULL,
	"object_type_id" bigint NOT NULL,
	"name" character varying NOT NULL,
	"description" character varying,
	CONSTRAINT OBJECTS_pk PRIMARY KEY ("object_id")
) WITH (
  OIDS=FALSE
);

CREATE TABLE "OBJECT_TYPES" (
	"object_type_id" bigint NOT NULL,
	"name" character varying NOT NULL,
	"parent_id" bigint,
	CONSTRAINT OBJECT_TYPES_pk PRIMARY KEY ("object_type_id")
) WITH (
  OIDS=FALSE
);

CREATE TABLE "ATTRIBUTES" (
	"attr_id" bigint NOT NULL,
	"attr_type_id" bigint NOT NULL,
	"name" character varying NOT NULL,
	"def_value" character varying,
	CONSTRAINT ATTRIBUTES_pk PRIMARY KEY ("attr_id")
) WITH (
  OIDS=FALSE
);

CREATE TABLE "ATTR_OBJECT_TYPES" (
	"attr_id" bigint NOT NULL,
	"object_type_id" bigint NOT NULL,
	"options" bigint NOT NULL
) WITH (
  OIDS=FALSE
);

CREATE TABLE "REFERENCES" (
	"attr_id" bigint NOT NULL,
	"reference" bigint NOT NULL,
	"object_id" bigint NOT NULL
) WITH (
  OIDS=FALSE
);

CREATE TABLE "ATTR_TYPES" (
	"attr_type_id" bigint NOT NULL,
	"name" character varying NOT NULL,
	CONSTRAINT ATTR_TYPES_pk PRIMARY KEY ("attr_type_id")
) WITH (
  OIDS=FALSE
);

CREATE TABLE "PARAMS" (
	"attr_id" bigint NOT NULL,
	"object_id" bigint NOT NULL,
	"value" character varying,
	"date_value" timestamptz,
	"list_value_id" bigint
) WITH (
  OIDS=FALSE
);

CREATE TABLE "LIST_VALUES" (
	"list_value_id" bigint NOT NULL,
	"attr_id" bigint NOT NULL,
	"value" character varying NOT NULL,
	CONSTRAINT LIST_VALUES_pk PRIMARY KEY ("list_value_id")
) WITH (
  OIDS=FALSE
);

ALTER TABLE "OBJECTS" ADD CONSTRAINT "OBJECTS_fk0" FOREIGN KEY ("object_type_id") REFERENCES "OBJECT_TYPES"("object_type_id");

ALTER TABLE "ATTRIBUTES" ADD CONSTRAINT "ATTRIBUTES_fk0" FOREIGN KEY ("attr_type_id") REFERENCES "ATTR_TYPES"("attr_type_id");

ALTER TABLE "ATTR_OBJECT_TYPES" ADD CONSTRAINT "ATTR_OBJECT_TYPES_fk0" FOREIGN KEY ("attr_id") REFERENCES "ATTRIBUTES"("attr_id");
ALTER TABLE "ATTR_OBJECT_TYPES" ADD CONSTRAINT "ATTR_OBJECT_TYPES_fk1" FOREIGN KEY ("object_type_id") REFERENCES "OBJECT_TYPES"("object_type_id");

ALTER TABLE "REFERENCES" ADD CONSTRAINT "REFERENCES_fk0" FOREIGN KEY ("attr_id") REFERENCES "ATTRIBUTES"("attr_id");
ALTER TABLE "REFERENCES" ADD CONSTRAINT "REFERENCES_fk1" FOREIGN KEY ("reference") REFERENCES "OBJECTS"("object_id") ON DELETE CASCADE;
ALTER TABLE "REFERENCES" ADD CONSTRAINT "REFERENCES_fk2" FOREIGN KEY ("object_id") REFERENCES "OBJECTS"("object_id") ON DELETE CASCADE;
ALTER TABLE "REFERENCES" ADD CONSTRAINT "REFERENCES_unique0" UNIQUE ("attr_id", "reference", "object_id");

ALTER TABLE "PARAMS" ADD CONSTRAINT "PARAMS_fk0" FOREIGN KEY ("attr_id") REFERENCES "ATTRIBUTES"("attr_id");
ALTER TABLE "PARAMS" ADD CONSTRAINT "PARAMS_fk1" FOREIGN KEY ("object_id") REFERENCES "OBJECTS"("object_id") ON DELETE CASCADE;
ALTER TABLE "PARAMS" ADD CONSTRAINT "PARAMS_unique0" UNIQUE ("attr_id", "object_id");

ALTER TABLE "LIST_VALUES" ADD CONSTRAINT "LIST_VALUES_fk0" FOREIGN KEY ("attr_id") REFERENCES "ATTRIBUTES"("attr_id");