-- Table: public.extras_pending

-- DROP TABLE public.extras_pending;

CREATE TABLE public.extras_pending
(
  item_id integer NOT NULL,
  paid integer,
  user_id integer NOT NULL,
  CONSTRAINT user_id_fkey FOREIGN KEY (user_id)
      REFERENCES public.accounts (user_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT item_it_fkey FOREIGN KEY (item_id)
	REFERENCES public.items (item_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT extras_pending_id PRIMARY KEY (user_id, item_id)    
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.extras_pending
  OWNER TO queststore;
