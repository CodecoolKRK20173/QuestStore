-- Table: public.mentors

-- DROP TABLE public.mentors;

CREATE TABLE public.mentors
(
  user_id integer NOT NULL,
  name text,
  surname text,
  email text,
  CONSTRAINT user_id_fkey FOREIGN KEY (user_id)
      REFERENCES public.accounts (user_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT mentor_id PRIMARY KEY (user_id)    
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.mentors
  OWNER TO queststore;
