package de.vaadinbuch.mvxdemo.login.impl.view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.eventbus.EventBus;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import de.vaadinbuch.mvxdemo.UnsupportedViewTypeException;
import de.vaadinbuch.mvxdemo.login.impl.event.LoginAttemptEvent;
import de.vaadinbuch.mvxdemo.login.impl.event.PasswordChangeEvent;
import de.vaadinbuch.mvxdemo.login.impl.event.UserIdChangeEvent;

@RunWith(value = MockitoJUnitRunner.class)
public class VaadinLoginViewLogicTest {

	@Mock
	private VaadinLoginView viewMock;
	@Mock
	private TextField userIdFieldMock;
	@Mock
	private PasswordField passwordFieldMock;
	@Mock
	private Button loginButtonMock;
	@Mock
	private EventBus eventBusMock;

	private VaadinLoginViewLogic viewLogic;

	private TextChangeListener userIdFieldChangeListener;
	private TextChangeListener passwordFieldChangeListener;
	private ClickListener loginButtonClickListener;

	@Before
	public void setup() {

		Mockito.when(this.viewMock.getUserIdField()).thenReturn(this.userIdFieldMock);
		Mockito.when(this.viewMock.getPasswordField()).thenReturn(this.passwordFieldMock);
		Mockito.when(this.viewMock.getLoginButton()).thenReturn(this.loginButtonMock);

		this.viewLogic = new VaadinLoginViewLogic(this.viewMock, this.eventBusMock);

		ArgumentCaptor<TextChangeListener> userIdTclCaptor = ArgumentCaptor.forClass(TextChangeListener.class);
		Mockito.verify(this.userIdFieldMock).addTextChangeListener(userIdTclCaptor.capture());
		this.userIdFieldChangeListener = userIdTclCaptor.getValue();

		ArgumentCaptor<TextChangeListener> passwordTclCaptor = ArgumentCaptor.forClass(TextChangeListener.class);
		Mockito.verify(this.passwordFieldMock).addTextChangeListener(passwordTclCaptor.capture());
		this.passwordFieldChangeListener = passwordTclCaptor.getValue();

		ArgumentCaptor<ClickListener> clCaptor = ArgumentCaptor.forClass(ClickListener.class);
		Mockito.verify(this.loginButtonMock).addClickListener(clCaptor.capture());
		this.loginButtonClickListener = clCaptor.getValue();

		Mockito.verify(this.userIdFieldMock).setValue("");
		Mockito.verify(this.userIdFieldMock).focus();
		Mockito.verify(this.passwordFieldMock).setValue("");
		Mockito.verify(this.loginButtonMock).setEnabled(false);
	}

	@Test
	public void test_getUserId() {
		String testUserId = "admin";
		Mockito.when(this.userIdFieldMock.getValue()).thenReturn(testUserId);

		assertSame(testUserId, this.viewLogic.getUserId());
	}

	@Test
	public void test_getPassword() {
		String testPassword = "secret";
		Mockito.when(this.passwordFieldMock.getValue()).thenReturn(testPassword);

		assertSame(testPassword, this.viewLogic.getPassword());
	}

	@Test
	public void test_setLoginButtonEnabled() {
		this.viewLogic.setLoginButtonEnabled(true);

		Mockito.verify(this.loginButtonMock).setEnabled(true);
	}

	@Test(expected = NullPointerException.class)
	public void cannotCreateWithoutViewInstance() {
		new VaadinLoginViewLogic(null, null);
	}

	@Test
	public void shouldDelegateUserIdChangeToPresenter() {
		String currentUserIdValue = "bla";
		TextChangeEvent textChangeEventMock = Mockito.mock(TextChangeEvent.class);
		Mockito.when(textChangeEventMock.getText()).thenReturn(currentUserIdValue);

		this.userIdFieldChangeListener.textChange(textChangeEventMock);

		ArgumentCaptor<UserIdChangeEvent> captor = ArgumentCaptor.forClass(UserIdChangeEvent.class);
		Mockito.verify(this.eventBusMock).post(captor.capture());
		assertEquals(currentUserIdValue, captor.getValue().getCurrentUserId());
	}

	@Test
	public void shouldDelegatePasswordChangeToPresenter() {
		String currentPasswordValue = "bla";
		TextChangeEvent textChangeEventMock = Mockito.mock(TextChangeEvent.class);
		Mockito.when(textChangeEventMock.getText()).thenReturn(currentPasswordValue);

		this.passwordFieldChangeListener.textChange(textChangeEventMock);

		ArgumentCaptor<PasswordChangeEvent> captor = ArgumentCaptor.forClass(PasswordChangeEvent.class);
		Mockito.verify(this.eventBusMock).post(captor.capture());
		assertEquals(currentPasswordValue, captor.getValue().getCurrentPassword());
	}

	@Test
	public void shouldDelegateLoginButtonClickToPresenter() {
		String userId = "admin";
		String password = "secret";

		Mockito.when(userIdFieldMock.getValue()).thenReturn(userId);
		Mockito.when(passwordFieldMock.getValue()).thenReturn(password);

		this.loginButtonClickListener.buttonClick(new ClickEvent(Mockito.mock(Component.class)));

		Mockito.verify(this.eventBusMock).post(Mockito.any(LoginAttemptEvent.class));
	}

	@Test(expected = UnsupportedViewTypeException.class)
	public void shouldThrowExceptionOnWrongViewtype() {
		this.viewLogic.getViewAs(String.class);
	}

	@Test
	public void shouldReturnViewAsVaadinComponent() {
		assertNotNull(this.viewLogic.getViewAs(Component.class));
	}

	@Test
	public void shouldReturnViewAsVaadinVerticalLayout() {
		assertNotNull(this.viewLogic.getViewAs(VerticalLayout.class));
	}
}
