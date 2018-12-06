-- Table: public.class_mentors

-- DROP TABLE public.class_mentors;

CREATE TABLE public.class_mentors
(
  class_id integer NOT NULL,
  user_id integer NOT NULL,
  
  CONSTRAINT class_id_fkey FOREIGN KEY(class_id) REFERENCES public.classes (class_id),
  CONSTRAINT user_id_fkey FOREIGN KEY (user_id) REFERENCES public.accounts (user_id),
  CONSTRAINT cl_mentors_pkey PRIMARY KEY(class_id, user_id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.class_mentors
  OWNER TO queststore;
