-- DROP TABLE public.classes;

CREATE TABLE public.classes(
	class_id SERIAL PRIMARY KEY,
	name TEXT
)
WITH (
  OIDS=FALSE
  );
ALTER TABLE public.classes
  OWNER TO queststore;
