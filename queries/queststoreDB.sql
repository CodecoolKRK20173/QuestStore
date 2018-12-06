-- Database: queststore

-- DROP DATABASE queststore;

CREATE DATABASE queststore
  WITH OWNER = queststore
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'en_US.UTF-8'
       LC_CTYPE = 'en_US.UTF-8'
       CONNECTION LIMIT = -1;

COMMENT ON DATABASE queststore
  IS 'server DB for purposes of QuestStore Web App';

