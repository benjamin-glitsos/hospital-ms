CREATE FUNCTION insert_for_users_open(
    _username text
  , _email_address text
  , _first_name text
  , _last_name text
  , _other_names text
  , _password text
  , _additional_notes text
)
RETURNS void AS $$
BEGIN
    WITH insert_meta AS (
        INSERT INTO meta (metakey, created_by, additional_notes)
        VALUES (generate_random_metakey('users'), 1, _additional_notes)
        RETURNING id
    )
    INSERT INTO users (
        username
      , email_address
      , first_name
      , last_name
      , other_names
      , password
      , meta_id
    )
    VALUES (
        _username
      , _email_address
      , _first_name
      , _last_name
      , _other_names
      , sha1_encrypt(_password)
      , (SELECT id FROM insert_meta)
    );
END;
$$ LANGUAGE plpgsql;
