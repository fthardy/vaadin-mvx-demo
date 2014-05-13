package de.vaadinbuch.mvxdemo.login.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

import de.vaadinbuch.mvxdemo.login.LoginComponent;
import de.vaadinbuch.mvxdemo.login.LoginModel;
import de.vaadinbuch.mvxdemo.login.LoginView;

@RunWith(value = MockitoJUnitRunner.class)
public class LoginComponentImplTest {

	private LoginComponentImpl loginComponent;

	@Mock
	private LoginView viewMock;
	@Mock
	private LoginModel modelMock;
	@Mock
	private LoginComponent.LoginSuccessHandler successHandlerMock1;
	@Mock
	private LoginComponent.LoginSuccessHandler successHandlerMock2;
	@Mock
	private LoginComponent.LoginFailedHandler failedHandlerMock1;
	@Mock
	private LoginComponent.LoginFailedHandler failedHandlerMock2;

	@Before
	public void setup() {
		Mockito.when(this.modelMock.getMinUserIdLength()).thenReturn(5);
		Mockito.when(this.modelMock.getMinPasswordLength()).thenReturn(8);

		this.loginComponent = new LoginComponentImpl(this.modelMock, this.viewMock);
		this.loginComponent.addLoginSuccessHandler(this.successHandlerMock1);
		this.loginComponent.addLoginSuccessHandler(this.successHandlerMock2);
		this.loginComponent.addLoginSuccessHandler(this.successHandlerMock2);
		this.loginComponent.addLoginFailedHandler(this.failedHandlerMock1);
		this.loginComponent.addLoginFailedHandler(this.failedHandlerMock1);
		this.loginComponent.addLoginFailedHandler(this.failedHandlerMock2);

		Mockito.verify(this.viewMock).setPresenter(this.loginComponent);
	}

	@Test
	public void shouldDisableLoginButtonWhenUserIdIsBelowMinLength() {
		Mockito.when(this.viewMock.getPassword()).thenReturn("12345678");

		this.loginComponent.onUserIdChange("1234");

		Mockito.verify(this.viewMock).setLoginButtonEnabled(false);
	}

	@Test
	public void shouldDisableLoginButtonWhenPasswordIsBelowMinLength() {
		Mockito.when(this.viewMock.getUserId()).thenReturn("12345");

		this.loginComponent.onPasswordChange("1234567");

		Mockito.verify(this.viewMock).setLoginButtonEnabled(false);
	}

	@Test
	public void shouldEnableLoginButtonWhenUserIdAndPasswordHaveAtLeastMinLength() {
		Mockito.when(this.viewMock.getUserId()).thenReturn("12345");

		this.loginComponent.onPasswordChange("12345678");

		Mockito.verify(this.viewMock).setLoginButtonEnabled(true);
	}

	@Test
	public void shouldPassUserToLoginSuccessActionWhenLoginIsSuccessful() {
		String userId = "admin";
		String password = "test";

		Mockito.when(this.viewMock.getUserId()).thenReturn(userId);
		Mockito.when(this.viewMock.getPassword()).thenReturn(password);
		Mockito.when(this.modelMock.loginUser(userId, password)).thenReturn(true);

		this.loginComponent.onLogin();

		Mockito.verify(this.successHandlerMock1).onLoginSuccess(userId);
		Mockito.verify(this.successHandlerMock2).onLoginSuccess(userId);
	}

	@Test
	public void shouldResetViewOnLoginFail() {
		String userId = "admin";
		String password = "test";

		Mockito.when(this.viewMock.getUserId()).thenReturn(userId);
		Mockito.when(this.viewMock.getPassword()).thenReturn(password);
		Mockito.when(this.modelMock.loginUser(userId, password)).thenReturn(false);

		this.loginComponent.onLogin();

		Mockito.verify(this.failedHandlerMock1).onLoginFailed();
		Mockito.verify(this.failedHandlerMock2).onLoginFailed();
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
