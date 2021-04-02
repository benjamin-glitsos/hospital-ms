-- CREATE FUNCTION friendly_name(
--     first_name text
--   , last_name text
--   , other_names text
--   )
-- RETURNS text
-- AS $$
--     CASE WHEN other_names IS NULL
--         THEN SELECT string_agg({first_name, last_name}, " ")
--     ELSE
--         SELECT concat(first_name, other_names, last_name)
-- $$ LANGUAGE SQL STRICT IMMUTABLE;
