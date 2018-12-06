-- Table: public.accounts

--DROP TABLE public.accounts;

CREATE TABLE public.accounts
(
  user_id SERIAL PRIMARY KEY,
  nick text NOT NULL UNIQUE,
  type text NOT NULL,
  password text NOT NULL,
  session_id TEXT UNIQUE
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.accounts
  OWNER TO queststore;
