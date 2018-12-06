-- Table: public.gained_items

-- DROP TABLE public.gained_items;

CREATE TABLE public.gained_items
(
  item_id INTEGER NOT NULL,
  used BOOLEAN,
  user_id INTEGER NOT NULL,
  CONSTRAINT user_id_fkey FOREIGN KEY (user_id)
      REFERENCES public.accounts (user_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT item_it_fkey FOREIGN KEY (item_id)
	REFERENCES public.items (item_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT gained_items_id PRIMARY KEY (user_id, item_id)    
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.gained_items
  OWNER TO queststore;
