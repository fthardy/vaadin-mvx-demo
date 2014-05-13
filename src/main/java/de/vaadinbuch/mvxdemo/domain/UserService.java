package de.vaadinbuch.mvxdemo.domain;

/**
 * Die Interfacedefinition des Services für den Zugriff auf die Benutzer.
 * 
 * @author Frank Hardy
 */
public interface UserService {

    /**
     * Liefert einen Benutzer.
     * 
     * @param userId
     *            die Kennung des Benutzers.
     * 
     * @return das Benutzerobjekt oder <code>null</code>, wenn kein Benutzer mit
     *         der angegebenen Kennung existiert.
     */
    User getUser(String userId);

    /**
     * Prüft die Benutzerzugangsdaten.
     * 
     * @param userId
     *            die Benutzerkennung.
     * @param password
     *            das Passwort.
     * 
     * @return <code>true</code> wenn die Zugangsdaten korrekt sind. Andernfalls
     *         <code>false</code>.
     */
    boolean checkCredentials(String userId, String password);

    /**
     * @return die Mindestlänge der Benutzerkennung.
     */
    int getUserIdMinLength();

    /**
     * @return die Mindestlänge des Passworts.
     */
    int getPasswordMinLength();

    /**
     * Speichert einen Benutzer.
     * 
     * @param user
     *            das zu speichernde Benutzerobjekt.
     */
    void storeUser(User user);
}
