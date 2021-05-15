package persistance;

import entities.User;

public interface ConfigDAO {
    boolean setVolumePiano(String nick, float value);
    boolean setVolumeSong(String nick, float value);
    entities.Config getConfig(String nick);
}
