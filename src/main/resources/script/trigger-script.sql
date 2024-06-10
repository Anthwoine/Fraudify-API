CREATE TRIGGER `update_listen_count`
    AFTER INSERT ON `history`
    FOR EACH ROW
BEGIN
    UPDATE listen
    SET listen_count = listen_count + 1
    WHERE music_id = NEW.music_id AND user_id = NEW.user_id;
END;


