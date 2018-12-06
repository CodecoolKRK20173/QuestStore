-- Table: public.teams

-- DROP TABLE public.teams;

CREATE TABLE public.teams
(
  team_id SERIAL NOT NULL PRIMARY KEY,
  name text    
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.teams
  OWNER TO queststore;
