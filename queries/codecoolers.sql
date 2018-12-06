-- Table: public.codecoolers

-- DROP TABLE public.codecoolers;

CREATE TABLE public.codecoolers
(
  user_id integer UNIQUE NOT NULL,
  nick text NOT NULL,
  balance integer,
  earned integer,
  class_id integer,
  CONSTRAINT codecoolers_user_id_fkey FOREIGN KEY (user_id)
      REFERENCES public.accounts (user_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT cc_id PRIMARY KEY (user_id),
  CONSTRAINT class_id_fkey FOREIGN KEY (class_id)
      REFERENCES public.classes (class_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.codecoolers
  OWNER TO queststore;
