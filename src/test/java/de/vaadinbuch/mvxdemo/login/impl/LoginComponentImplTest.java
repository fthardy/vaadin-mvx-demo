package de.vaadinbuch.mvxdemo.login.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.enterprise.event.Event;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

import de.vaadinbuch.mvxdemo.login.LoginModel;
import de.vaadinbuch.mvxdemo.login.LoginView;
import de.vaadinbuch.mvxdemo.login.event.LoginFailedEvent;
import de.vaadinbuch.mvxdemo.login.event.LoginSuccessEvent;
import de.vaadinbuch.mvxdemo.login.impl.event.LoginAttemptEvent;
import de.vaadinbuch.mvxdemo.login.impl.event.PasswordChangeEvent;
import de.vaadinbuch.mvxdemo.login.impl.event.UserIdChangeEvent;

@RunWith(value = MockitoJUnitRunner.class)
public class LoginComponentImplTest {

	private LoginComponentImpl loginComponent;

	@Mock
	private LoginView viewMock;
	@Mock
	private LoginModel modelMock;
	@Mock
	private Event<LoginSuccessEvent> loginSuccessEventSinkMock;
	@Mock
	private Event<LoginFailedEvent> loginFailedEventSinkMock;

	@Before
	public void setup() {
		Mockito.when(this.modelMock.getMinUserIdLength()).thenReturn(5);
		Mockito.when(this.modelMock.getMinPasswordLength()).thenReturn(8);

		this.loginComponent = new LoginComponentImpl(this.modelMock, this.viewMock);
		this.loginComponent.loginSuccessEventSink = this.loginSuccessEventSinkMock;
		this.loginComponent.loginFailedEventSink = this.loginFailedEventSinkMock;
	}

	@Test
	public void shouldDisableLoginButtonWhenUserIdIsBelowMinLength() {
		Mockito.when(this.viewMock.getPassword()).thenReturn("12345678");

		this.loginComponent.onUserIdChange(new UserIdChangeEvent(this.viewMock, "1234"));

		Mockito.verify(this.viewMock).setLoginButtonEnabled(false);
	}

	@Test
	public void shouldDisableLoginButtonWhenPasswordIsBelowMinLength() {
		Mockito.when(this.viewMock.getUserId()).thenReturn("12345");

		this.loginComponent.onPasswordChange(new PasswordChangeEvent(this.viewMock, "1234567"));

		Mockito.verify(this.viewMock).setLoginButtonEnabled(false);
	}

	@Test
	public void shouldEnableLoginButtonWhenUserIdAndPasswordHaveAtLeastMinLength() {
		Mockito.when(this.viewMock.getUserId()).thenReturn("12345");

		this.loginComponent.onPasswordChange(new PasswordChangeEvent(this.viewMock, "12345678"));

		Mockito.verify(this.viewMock).setLoginButtonEnabled(true);
	}

	@Test
	public void shouldPassUserToLoginSuccessActionWhenLoginIsSuccessful() {
		String userId = "admin";
		String password = "test";

		Mockito.when(this.viewMock.getUserId()).thenReturn(userId);
		Mockito.when(this.viewMock.getPassword()).thenReturn(password);
		Mockito.when(this.modelMock.loginUser(userId, password)).thenReturn(true);

		this.loginComponent.onLogin(new LoginAttemptEvent(this.viewMock));

		Mockito.verify(this.loginSuccessEventSinkMock).fire(Mockito.any(LoginSuccessEvent.class));
	}

	@Test
	public void shouldResetViewOnLoginFail() {
		String userId = "admin";
		String password = "test";

		Mockito.when(this.viewMock.getUserId()).thenReturn(userId);
		Mockito.when(this.viewMock.getPassword()).thenReturn(password);
		Mockito.when(this.modelMock.loginUser(userId, password)).thenReturn(false);

		this.loginComponent.onLogin(new LoginAttemptEvent(this.viewMock));

		Mockito.verify(this.loginFailedEventSinkMock).fire(Mockito.any(LoginFailedEvent.class));
		Mockito.verify(this.viewMock).reset();
	}

	@Test
	public void shouldDelegateViewAccessToView() {
		Mockito.when(this.viewMock.getViewAs(Component.class)).thenReturn(new VerticalLayout());

		Component view = this.loginComponent.getViewAs(Component.class);

		assertNotNull(view);
		assertEquals(VerticalLayout.class, view.getClass());
	}
}
