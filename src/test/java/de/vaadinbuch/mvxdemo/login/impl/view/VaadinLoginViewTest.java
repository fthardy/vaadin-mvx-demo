package de.vaadinbuch.mvxdemo.login.impl.view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.server.Sizeable;

import de.vaadinbuch.mvxdemo.login.LoginView;

@RunWith(value = MockitoJUnitRunner.class)
public class VaadinLoginViewTest {

	@Mock
	private LoginView.Presenter presenterMock;

	private VaadinLoginView view = new VaadinLoginView();

	@Before
	public void setup() {
		this.view = new VaadinLoginView();
		this.view.setPresenter(this.presenterMock);
	}

	@Test
	public void shouldBypassEventHandlingWhenNoPresenterIsSet() {
		VaadinLoginView viewWithoutPresenter = new VaadinLoginView();
		viewWithoutPresenter.onUserIdChange(null);
		viewWithoutPresenter.onPasswordChange(null);
		viewWithoutPresenter.onLoginButtonClicked();
	}

	@Test
	public void shouldSetLoginButtonEnabledState() {
		assertFalse(this.view.loginBTN.isEnabled());
		this.view.setLoginButtonEnabled(true);
		assertTrue(this.view.loginBTN.isEnabled());
	}

	@Test
	public void shouldHavePrimaryStyleAndUndefinedSize() {
		assertEquals(Sizeable.SIZE_UNDEFINED, this.view.getHeight(), 0);
		assertEquals(Sizeable.SIZE_UNDEFINED, this.view.getWidth(), 0);
	}

	@Test
	public void shouldInitiallyReturnEmptyUserIdAndPassword() {
		assertEquals("", this.view.getUserId());
		assertEquals("", this.view.getPassword());
	}

	@Test
	public void shouldInitiallyDisableButton() {
		assertFalse(this.view.loginBTN.isEnabled());
	}

	@Test
	public void resetShouldClearUserIdAndPasswordFieldsAndDisableButton() {
		this.view.loginBTN.setEnabled(true);
		this.view.userIdTF.setValue("test");
		this.view.passwordField.setValue("secret");

		this.view.reset();

		this.shouldInitiallyReturnEmptyUserIdAndPassword();
		this.shouldInitiallyDisableButton();
	}

	@Test
	public void shouldDelegateToPresenter_onUserIdChange() {
		TextChangeEvent userIdChangeEvent = Mockito.mock(TextChangeEvent.class);
		String userIdCurrentValue = "banana!";
		Mockito.when(userIdChangeEvent.getText()).thenReturn(userIdCurrentValue);

		this.view.onUserIdChange(userIdChangeEvent);

		Mockito.verify(this.presenterMock).onUserIdChange(userIdCurrentValue);
	}

	@Test
	public void shouldDelegateToPresenter_onPasswordChange() {
		TextChangeEvent passwordChangeEvent = Mockito.mock(TextChangeEvent.class);
		String passwordCurrentValue = "apple!";
		Mockito.when(passwordChangeEvent.getText()).thenReturn(passwordCurrentValue);

		this.view.onPasswordChange(passwordChangeEvent);

		Mockito.verify(this.presenterMock).onPasswordChange(passwordCurrentValue);
	}

	@Test
	public void shouldDelegateToPresenter_onLoginButtonClicked() {
		String userId = "admin";
		String password = "secret";

		this.view.userIdTF.setValue(userId);
		this.view.passwordField.setValue(password);

		this.view.onLoginButtonClicked();

		Mockito.verify(this.presenterMock).onLogin();
	}
}
