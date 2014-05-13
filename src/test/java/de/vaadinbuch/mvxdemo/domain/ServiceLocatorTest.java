package de.vaadinbuch.mvxdemo.domain;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Ein einfacher Test um zu sehen, ob der ServiceLocator den {@link UserService}
 * zur Verfügung stellt.
 * 
 * @author Frank Hardy
 */
public class ServiceLocatorTest {

	@Test
	public void testAccessToUserService() {
		UserService service = ServiceLocator.getInstance().getService(UserService.class);
		assertNotNull(service);
		assertTrue(service instanceof DummyUserService);
	}
}
