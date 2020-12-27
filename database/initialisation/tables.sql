CREATE TABLE meta (
    id SERIAL PRIMARY KEY
  , uuid UUID UNIQUE NOT null DEFAULT gen_random_uuid()
  , opens INTEGER NOT null DEFAULT 0
  , edits INTEGER NOT null DEFAULT 0
  , is_deleted BOOLEAN NOT null DEFAULT false
  , created_at TIMESTAMP NOT null DEFAULT NOW()
  , edited_at TIMESTAMP
  , deleted_at TIMESTAMP
  , restored_at TIMESTAMP
  , notes TEXT NOT null DEFAULT ''
);

CREATE TABLE users (
    id SERIAL PRIMARY KEY
  , meta_id SMALLINT UNIQUE NOT null
  , username VARCHAR(20) UNIQUE NOT null
  , password VARCHAR(20) NOT null
  , email_address EMAIL_ADDRESS NOT null
);
