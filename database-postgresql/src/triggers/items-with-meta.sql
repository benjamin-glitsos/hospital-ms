CREATE FUNCTION items_with_meta_trigger()
RETURNS trigger
AS $$
BEGIN
    IF TG_OP = 'INSERT' THEN
        PERFORM insert_for_items_with_meta(
            NEW.key
          , NEW.name
          , NEW.description
          , NEW.notes
        );

    ELSIF TG_OP = 'UPDATE' AND NEW.opens > OLD.opens THEN
        PERFORM open_for_items_with_meta(
            OLD.key
        );

    ElSIF TG_OP = 'UPDATE' AND NEW.is_deleted=true AND OLD.is_deleted=false THEN
        PERFORM soft_delete_for_items_with_meta(
            OLD.key
        );

    ELSIF TG_OP = 'UPDATE' AND NEW.is_deleted=false AND OLD.is_deleted=true THEN
        PERFORM restore_delete_for_items_with_meta(
            OLD.key
        );

    ELSIF TG_OP = 'DELETE' THEN
        PERFORM hard_delete_for_items_with_meta(
            OLD.key
        );

    ELSIF TG_OP = 'UPDATE'
        AND NEW.* IS DISTINCT FROM OLD.*
        AND OLD.is_deleted=false THEN
        PERFORM update_for_items_with_meta(
            OLD.key
          , NEW.key
          , NEW.name
          , NEW.description
          , NEW.notes
        );

    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER items_with_meta
    INSTEAD OF INSERT OR UPDATE OR DELETE ON items_with_meta
    FOR EACH ROW EXECUTE PROCEDURE items_with_meta_trigger();