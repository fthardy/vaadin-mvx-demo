package de.vaadinbuch.mvxdemo.domain;

import java.security.MessageDigest;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

/**
 * Dummyimplementierung des {@link UserService}.
 * 
 * @author Frank Hardy
 */
@ApplicationScoped
public class DummyUserService implements UserService {

	private final Map<String, User> userMap = new HashMap<String, User>();

	public DummyUserService() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, 1975);
		calendar.set(Calendar.MONTH, 6);
		calendar.set(Calendar.DAY_OF_MONTH, 12);

		this.storeUser(this.createUser("admin", "test1234", "Jo", "Admin", "joadmin@coolcompany.de", calendar.getTime()));
	}

	@Override
	public int getUserIdMinLength() {
		return 5;
	}

	@Override
	public int getPasswordMinLength() {
		return 8;
	}

	@Override
	public User getUser(String userId) {
		return new User(this.userMap.get(userId));
	}

	@Override
	public boolean checkCredentials(String userId, String password) {
		User user = this.userMap.get(userId);

		boolean success = false;
		if (user != null) {
			success = user.getPasswordHash().equals(this.generatePasswordHash(password));
		}
		return success;
	}

	@Override
	public void storeUser(User user) {
		this.userMap.put(user.getUserId(), user);
	}

	private User createUser(String userId, String password, String firstName, String lastName, String email,
			Date dateOfBirth) {
		User user = new User();
		if (userId.length() < this.getUserIdMinLength()) {
			throw new IllegalArgumentException("Die Benutzerkennung ist zu kurz!");
		}
		user.setUserId(userId);
		if (password.length() < this.getPasswordMinLength()) {
			throw new IllegalArgumentException("Das Passwort ist zu kurz!");
		}
		user.setPasswordHash(this.generatePasswordHash(password));
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmailAddress(email);
		user.setDateOfBirth(dateOfBirth);

		return user;
	}

	private String generatePasswordHash(String password) {
		try {
			MessageDigest passwordHasher;
			passwordHasher = MessageDigest.getInstance("SHA-256");
			passwordHasher.update(password.getBytes("UTF-16"));
			return new String(passwordHasher.digest());
		} catch (Exception e) {
			throw new RuntimeException("Die Verschlüsselung des Passwortes ist fehlgeschlagen!", e);
		}
	}
}
