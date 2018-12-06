-- Table: public.items

-- DROP TABLE public.items;

CREATE TABLE public.items
(
  item_id SERIAL PRIMARY KEY,
  i_name TEXT NOT NULL UNIQUE,
  description TEXT,
  price INTEGER,
  type TEXT
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.items
  OWNER TO queststore;
