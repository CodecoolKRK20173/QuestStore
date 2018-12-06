-- Table: public.quests

-- DROP TABLE public.quests;

CREATE TABLE public.quests
(
  quest_id SERIAL PRIMARY KEY,
  q_name TEXT NOT NULL UNIQUE,
  description TEXT,
  coin_reward TEXT,
  type TEXT,
  quest_type TEXT
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.quests
  OWNER TO queststore;
