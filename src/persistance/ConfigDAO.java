package persistance;

public interface ConfigDAO {
    boolean setVolumePiano(String nick, float value);
    boolean setVolumeSong(String nick, float value);
    entities.Config getConfig(String nick);
    boolean setConfig(String nick, char[] characters);
    boolean deleteUserConfig(String nick);
}
