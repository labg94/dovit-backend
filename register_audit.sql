CREATE OR REPLACE FUNCTION registerAudit(
    data text,
    message varchar,
    status varchar,
    user_id bigint
)
    RETURNS boolean AS $$
DECLARE
    count    INTEGER;
BEGIN
    INSERT INTO audit(action_date, data, message, status, user_id, database_user)
    VALUES (NOW(), data::jsonb, message, status, user_id, (SELECT current_user));
    GET DIAGNOSTICS count = ROW_COUNT;
    RETURN count > 0;
END;
$$ LANGUAGE plpgsql;