package de.vaadinbuch.mvxdemo.login.impl.view;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

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
import de.vaadinbuch.mvxdemo.login.LoginPresentationModel;
import de.vaadinbuch.mvxdemo.login.impl.event.LoginEnabledChangedEvent;
import de.vaadinbuch.mvxdemo.login.impl.event.ResetLoginViewEvent;

@RunWith(value = MockitoJUnitRunner.class)
public class VaadinLoginViewLogicTest {

	@Mock
	private VaadinLoginView viewMock;
	@Mock
	private LoginPresentationModel presentationModelMock;

	private TextField userIdFieldMock = Mockito.spy(new TextField());
	private PasswordField passwordFieldMock = Mockito.spy(new PasswordField());
	private Button loginButtonMock = Mockito.spy(new Button());

	private VaadinLoginViewLogic viewLogic;

	private TextChangeListener userIdFieldChangeListener;
	private TextChangeListener passwordFieldChangeListener;
	private ClickListener loginButtonClickListener;

	@SuppressWarnings("unchecked")
	@Before
	public void setup() {
		Mockito.when(this.presentationModelMock.getUserId()).thenReturn("");
		Mockito.when(this.presentationModelMock.getPassword()).thenReturn("");
		Mockito.when(this.presentationModelMock.isLoginEnabled()).thenReturn(false);

		Mockito.when(this.viewMock.getUserIdField()).thenReturn(this.userIdFieldMock);
		Mockito.when(this.viewMock.getPasswordField()).thenReturn(this.passwordFieldMock);
		Mockito.when(this.viewMock.getLoginButton()).thenReturn(this.loginButtonMock);

		this.viewLogic = new VaadinLoginViewLogic(this.viewMock, this.presentationModelMock);

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

		Mockito.reset(this.userIdFieldMock, this.passwordFieldMock, this.loginButtonMock);
	}

	@Test
	public void shouldNotResetViewOnResetEventFromDifferentPresentationModel() {
		this.viewLogic.onReset(new ResetLoginViewEvent(Mockito.mock(LoginPresentationModel.class)));

		Mockito.verify(this.userIdFieldMock, Mockito.never()).setValue("");
		Mockito.verify(this.userIdFieldMock, Mockito.never()).focus();
		Mockito.verify(this.passwordFieldMock, Mockito.never()).setValue("");
		Mockito.verify(this.loginButtonMock, Mockito.never()).setEnabled(false);
	}

	@Test
	public void shouldResetViewWhenReceiveResetEvent() {
		this.viewLogic.onReset(new ResetLoginViewEvent(this.presentationModelMock));

		Mockito.verify(this.userIdFieldMock).setValue("");
		Mockito.verify(this.userIdFieldMock).focus();
		Mockito.verify(this.passwordFieldMock).setValue("");
		Mockito.verify(this.loginButtonMock).setEnabled(false);
	}

	@Test
	public void shouldNotSetLoginButtonEnabledStateOnChangeEventFromDifferentPresentationModel() {
		this.viewLogic.onLoginEnabledChanged(new LoginEnabledChangedEvent(Mockito.mock(LoginPresentationModel.class)));
		Mockito.verify(this.loginButtonMock, Mockito.never()).setEnabled(false);
	}

	@Test
	public void shouldSetLoginButtonEnabledStateOnChangeEvent() {
		this.viewLogic.onLoginEnabledChanged(new LoginEnabledChangedEvent(this.presentationModelMock));
		Mockito.verify(this.loginButtonMock).setEnabled(false);
	}

	@Test
	public void shouldDelegateUserIdChangeToPresenter() {
		String currentUserIdValue = "bla";
		TextChangeEvent textChangeEventMock = Mockito.mock(TextChangeEvent.class);
		Mockito.when(textChangeEventMock.getText()).thenReturn(currentUserIdValue);

		this.userIdFieldChangeListener.textChange(textChangeEventMock);
		Mockito.verify(this.presentationModelMock).onUserIdChange(currentUserIdValue);
	}

	@Test
	public void shouldDelegatePasswordChangeToPresenter() {
		String currentPasswordValue = "bla";
		TextChangeEvent textChangeEventMock = Mockito.mock(TextChangeEvent.class);
		Mockito.when(textChangeEventMock.getText()).thenReturn(currentPasswordValue);

		this.passwordFieldChangeListener.textChange(textChangeEventMock);
		Mockito.verify(this.presentationModelMock).onPasswordChange(currentPasswordValue);
	}

	@Test
	public void shouldDelegateLoginButtonClickToPresenter() {
		this.loginButtonClickListener.buttonClick(new ClickEvent(Mockito.mock(Component.class)));
		Mockito.verify(this.presentationModelMock).onLogin();
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
