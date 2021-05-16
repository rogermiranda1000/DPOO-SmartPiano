package persistance;

import entities.User;

public interface ConfigDAO {
    boolean setVolumePiano(String nick, float value);
    Float getVolumePiano(String nick);

    boolean setVolumeSong(String nick, float value);
    Float getVolumeSong(String nick);
}
