package persistance;

/**
 * Interface used for accessing the user's configuration
 */
public interface ConfigDAO {

    /**
     * Saves a new piano volume at the ddbb
     * @param nick Username of the logged user
     * @param value Value of the new piano volume (1 = full volume, 0 = silent)
     * @return True if the operation was successful
     */
    boolean setVolumePiano(String nick, float value);

    /**
     * Saves a new song volume at the ddbb
     * @param nick Username of the logged user
     * @param value Value of the new song volume (1 = full volume, 0 = silent)
     * @return True if the operation was successful
     */
    boolean setVolumeSong(String nick, float value);

    /**
     * Returns the configuration of the specified user
     * @param nick Username of the logged user
     * @return The configuration as a Config object, with the piano volume, song volume and noteBinds
     */
    entities.Config getConfig(String nick);

    /**
     * Saves a key configuration for the specified user
     * @param nick Username of the logged user
     * @param characters Array of characters corresponding to the new configuration,
     * @return True if the operation was successful
     */
    boolean setConfig(String nick, char[] characters);

    /**
     * Deletes the configuration of the specified user
     * @param nick Username of the user
     * @return True if the operation was successful
     */
    boolean deleteUserConfig(String nick);
}
