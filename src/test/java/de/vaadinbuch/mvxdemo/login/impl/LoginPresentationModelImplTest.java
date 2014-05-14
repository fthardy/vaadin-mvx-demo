package de.vaadinbuch.mvxdemo.login.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.enterprise.event.Event;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import de.vaadinbuch.mvxdemo.login.LoginModel;
import de.vaadinbuch.mvxdemo.login.event.LoginFailedEvent;
import de.vaadinbuch.mvxdemo.login.event.LoginSuccessEvent;
import de.vaadinbuch.mvxdemo.login.impl.event.LoginEnabledChangedEvent;
import de.vaadinbuch.mvxdemo.login.impl.event.ResetLoginViewEvent;

@RunWith(value = MockitoJUnitRunner.class)
public class LoginPresentationModelImplTest {

	private LoginPresentationModelImpl presentationModel;

	@Mock
	private LoginModel modelMock;
	@Mock
	private Event<LoginSuccessEvent> loginSuccessEventSinkMock;
	@Mock
	private Event<LoginFailedEvent> loginFailedEventSinkMock;
	@Mock
	private Event<ResetLoginViewEvent> resetLoginViewEventSinkMock;
	@Mock
	private Event<LoginEnabledChangedEvent> loginEnabledChangedEventSinkMock;

	@Before
	public void setup() {
		this.presentationModel = new LoginPresentationModelImpl(this.modelMock);
		this.presentationModel.loginSuccessEventSink = this.loginSuccessEventSinkMock;
		this.presentationModel.loginFailedEventSink = this.loginFailedEventSinkMock;
		this.presentationModel.loginEnabledChangedEventSink = this.loginEnabledChangedEventSinkMock;
		this.presentationModel.resetLoginViewEventSink = this.resetLoginViewEventSinkMock;

		Mockito.when(this.modelMock.getMinUserIdLength()).thenReturn(5);
		Mockito.when(this.modelMock.getMinPasswordLength()).thenReturn(8);
	}

	@Test
	public void shouldEnableLoginButtonWhenUserIdAndPasswordHaveMinLength() {
		this.presentationModel.setPassword("secretkey");
		this.presentationModel.onUserIdChange("admin");

		Mockito.verify(this.loginEnabledChangedEventSinkMock).fire(Mockito.any(LoginEnabledChangedEvent.class));

		assertTrue(this.presentationModel.isLoginEnabled());
	}

	@Test
	public void shouldDisableLoginButtonWhenUserIdAndPasswordDoNotHaveMinLength() {
		this.presentationModel.setPassword("");
		this.presentationModel.onUserIdChange("a");

		Mockito.verify(this.loginEnabledChangedEventSinkMock).fire(Mockito.any(LoginEnabledChangedEvent.class));

		assertFalse(this.presentationModel.isLoginEnabled());
	}

	@Test
	public void shouldDisableLoginButtonWhenPasswordDoesNotHaveMinLength() {
		this.presentationModel.setUserId("admin");
		this.presentationModel.onPasswordChange("bla");

		Mockito.verify(this.loginEnabledChangedEventSinkMock).fire(Mockito.any(LoginEnabledChangedEvent.class));

		assertFalse(this.presentationModel.isLoginEnabled());
	}

	@Test
	public void shouldDisableLoginButtonWhenUserIdDoesNotHaveMinLength() {
		this.presentationModel.setUserId("");
		this.presentationModel.onPasswordChange("secretkey");

		Mockito.verify(this.loginEnabledChangedEventSinkMock).fire(Mockito.any(LoginEnabledChangedEvent.class));

		assertFalse(this.presentationModel.isLoginEnabled());
	}

	@Test
	public void testSuccessfulLogin() {
		String userId = "admin";
		String password = "secret";

		this.presentationModel.setUserId(userId);
		this.presentationModel.setPassword(password);
		this.presentationModel.setLoginEnabled(true);

		Mockito.when(this.modelMock.loginUser(userId, password)).thenReturn(true);

		this.presentationModel.onLogin();

		Mockito.verify(this.loginSuccessEventSinkMock).fire(Mockito.any(LoginSuccessEvent.class));

		assertEquals("", this.presentationModel.getUserId());
		assertEquals("", this.presentationModel.getPassword());
		assertFalse(this.presentationModel.isLoginEnabled());

		Mockito.verify(this.resetLoginViewEventSinkMock).fire(Mockito.any(ResetLoginViewEvent.class));
	}

	@Test
	public void testFailedLogin() {
		String userId = "admin";
		String password = "secret";

		this.presentationModel.setUserId(userId);
		this.presentationModel.setPassword(password);
		this.presentationModel.setLoginEnabled(true);

		Mockito.when(this.modelMock.loginUser(userId, password)).thenReturn(false);

		this.presentationModel.onLogin();

		Mockito.verify(this.loginFailedEventSinkMock).fire(Mockito.any(LoginFailedEvent.class));

		assertEquals("", this.presentationModel.getUserId());
		assertEquals("", this.presentationModel.getPassword());
		assertFalse(this.presentationModel.isLoginEnabled());

		Mockito.verify(this.resetLoginViewEventSinkMock).fire(Mockito.any(ResetLoginViewEvent.class));
	}
}
