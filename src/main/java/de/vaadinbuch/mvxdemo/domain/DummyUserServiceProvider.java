package de.vaadinbuch.mvxdemo.domain;

/**
 * Die ServiceProvider-Implementierung für den {@link DummyUserService}.
 * 
 * @author Frank Hardy
 */
public class DummyUserServiceProvider implements ServiceProvider<UserService> {

	private final UserService serviceInstance = new DummyUserService();

	@Override
	public Class<UserService> getServiceType() {
		return UserService.class;
	}

	@Override
	public UserService getService() {
		return this.serviceInstance;
	}
}
