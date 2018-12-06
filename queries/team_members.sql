-- Table: public.team_memebers

-- DROP TABLE public.team_members;

CREATE TABLE public.team_members
(
  team_id integer NOT NULL,
  user_id integer NOT NULL,
  CONSTRAINT members_pkey PRIMARY KEY(team_id, user_id),
  CONSTRAINT team_id_fkey FOREIGN KEY(team_id) REFERENCES public.teams (team_id),
  CONSTRAINT user_id_fkey FOREIGN KEY (user_id) REFERENCES public.accounts (user_id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.team_members
  OWNER TO queststore;
