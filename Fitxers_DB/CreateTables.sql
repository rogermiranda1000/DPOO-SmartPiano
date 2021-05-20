-- Delete all tables
DROP TABLE IF EXISTS SongNotes CASCADE;
DROP TABLE IF EXISTS ListSongs CASCADE;
DROP TABLE IF EXISTS Listen CASCADE;
DROP TABLE IF EXISTS Ranking CASCADE;
DROP TABLE IF EXISTS Lists CASCADE;
DROP TABLE IF EXISTS Songs CASCADE;
DROP TABLE IF EXISTS PianoKeys CASCADE;
DROP TABLE IF EXISTS VirtualUsers CASCADE;
DROP TABLE IF EXISTS RegisteredUsers CASCADE;
DROP TABLE IF EXISTS Users CASCADE;

-- Update all tables
CREATE TABLE Users (
    id MEDIUMINT NOT NULL AUTO_INCREMENT,
    username VARCHAR(255),
    PRIMARY KEY (id, username)
);
CREATE TABLE RegisteredUsers (
    id MEDIUMINT NOT NULL,
    email VARCHAR(255) UNIQUE,
    password CHAR(32),
    volume_piano FLOAT,  -- Volume of the notes played in the piano (1 = max volume, 0 = silenced)
    volume_song FLOAT,   -- Volume of the song in the player (1 = max volume, 0 = silenced)
    PRIMARY KEY (id),
    FOREIGN KEY (id)
    REFERENCES Users(id)
);
CREATE TABLE VirtualUsers (
    id MEDIUMINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id)
    REFERENCES Users(id)
);
CREATE TABLE PianoKeys (
    note ENUM('Do', 'Do#', 'Re', 'Re#', 'Mi', 'Fa', 'Fa#', 'Sol', 'Sol#', 'La', 'La#', 'Si') NOT NULL, -- Note of the piano this represents
    octave TINYINT,
    user MEDIUMINT,      -- User that configured these settings
    keyboard CHAR(1),    -- Character/key related to the note
    PRIMARY KEY (note),
    FOREIGN KEY (user)
    REFERENCES RegisteredUsers(id)
);
CREATE TABLE Songs (
    id MEDIUMINT NOT NULL AUTO_INCREMENT,
    public BINARY(1),    -- 1 = public, 0 = private
    name VARCHAR(255),
    date DATE DEFAULT CURRENT_TIMESTAMP, -- Day when the song was published, default value = row creation date
    author MEDIUMINT,
    tick_length DOUBLE,
    PRIMARY KEY (id),
    FOREIGN KEY (author)
    REFERENCES Users(id)
);
CREATE TABLE Lists (
    id MEDIUMINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255),
    author MEDIUMINT,
    PRIMARY KEY (id),
    FOREIGN KEY (author)
    REFERENCES RegisteredUsers(id)
);
CREATE TABLE Ranking (
    user MEDIUMINT NOT NULL,
    song MEDIUMINT NOT NULL,
    points MEDIUMINT,
    PRIMARY KEY (user, song),
    FOREIGN KEY (user)
    REFERENCES RegisteredUsers(id),
    FOREIGN KEY (song)
    REFERENCES Songs(id)
);
CREATE TABLE Listen (
    date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,  -- Date when the reproduction took place, default value = row creation date
    user MEDIUMINT NOT NULL,
    song MEDIUMINT NOT NULL,
    seconds_listened MEDIUMINT,  -- How many seconds the user listened to the song that specific moment
    PRIMARY KEY (date, user, song),
    FOREIGN KEY (user)
    REFERENCES RegisteredUsers(id),
    FOREIGN KEY (song)
    REFERENCES Songs(id)
);
CREATE TABLE ListSongs (
    list MEDIUMINT NOT NULL,
    song MEDIUMINT NOT NULL,
    PRIMARY KEY (list, song),
    FOREIGN KEY (list)
    REFERENCES Lists(id),
    FOREIGN KEY (song)
    REFERENCES Songs(id)
);
CREATE TABLE SongNotes (
    -- Table that stores the info about a note press or release in a specific song
    note ENUM('Do', 'Do#', 'Re', 'Re#', 'Mi', 'Fa', 'Fa#', 'Sol', 'Sol#', 'La', 'La#', 'Si') NOT NULL,
    tick BIGINT NOT NULL,           -- Moment when the event happened
    pressed BINARY(1) NOT NULL,     -- 1 = the key was pressed, 0 = the key was released
    velocity TINYINT,               -- Volume of the note. 0 = silent, 127 = max volume
    octave TINYINT,
    song MEDIUMINT NOT NULL,
    PRIMARY KEY (note, tick, pressed, song),
    FOREIGN KEY (song)
    REFERENCES Songs(id)
);

-- Triggers
DROP TRIGGER IF EXISTS DefaultPianoKeys;
CREATE TRIGGER DefaultPianoKeys
    AFTER INSERT ON RegisteredUsers
    FOR EACH ROW
BEGIN
    INSERT INTO PianoKeys (note, octave, user, keyboard)
    VALUES ('Do', 1, NEW.id, 'a'),
           ('Do#', 1, NEW.id, 's'),
           ('Re', 1, NEW.id, 'd'),
           ('Re#', 1, NEW.id, 'f'),
           ('Mi', 1, NEW.id, 'g'),
           ('Fa', 1, NEW.id, 'h'),
           ('Fa#', 1, NEW.id, 'j'),
           ('Sol', 1, NEW.id, 'k'),
           ('Sol#', 1, NEW.id, 'l'),
           ('La', 1, NEW.id, 'ñ'),
           ('La#', 1, NEW.id, ','),
           ('Si', 1, NEW.id, '.'),
           ('Do', 2, NEW.id, 'q'),
           ('Do#', 2, NEW.id, 'w'),
           ('Re', 2, NEW.id, 'e'),
           ('Re#', 2, NEW.id, 'r'),
           ('Mi', 2, NEW.id, 't'),
           ('Fa', 2, NEW.id, 'y'),
           ('Fa#', 2, NEW.id, 'u'),
           ('Sol', 2, NEW.id, 'i'),
           ('Sol#', 2, NEW.id, 'o'),
           ('La', 2, NEW.id, 'p'),
           ('La#', 2, NEW.id, '9'),
           ('Si', 2, NEW.id, '0');
END;