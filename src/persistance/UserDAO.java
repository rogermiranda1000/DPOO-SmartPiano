package persistance;

import entities.User;

public interface UserDAO {
    /* REGISTERED USERS */
    boolean addUser(User user, String password);
    boolean deleteUser(User user, String password);

    /**
     * Retorna si un usuari (real; no virtual) existeix
     * @param nick Nick a comprobar
     * @return true si existeix, false si no, null si error
     */
    Boolean existsUser(String nick);

    /* LOGGED USERS */
    User getUser(String nick, String password);

    /* VIRTUAL USERS */

    /**
     * Afegeix un usuari "fals", el qual només coneixem el nom
     * @param nick Nom de l'usuari
     * @return Si s'ha afegit (true) o hi ha hagut algun problema (false)
     */
    boolean addVirtualUser(String nick);

    Boolean existsVirtualUser(String nick);
}
