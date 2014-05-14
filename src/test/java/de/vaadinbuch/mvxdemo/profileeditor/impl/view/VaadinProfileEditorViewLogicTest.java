package de.vaadinbuch.mvxdemo.profileeditor.impl.view;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import javax.enterprise.event.Event;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import de.vaadinbuch.mvxdemo.UnsupportedViewTypeException;
import de.vaadinbuch.mvxdemo.domain.User;
import de.vaadinbuch.mvxdemo.profileeditor.impl.event.CancelEditingEvent;
import de.vaadinbuch.mvxdemo.profileeditor.impl.event.SaveUserEvent;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Notification.class)
public class VaadinProfileEditorViewLogicTest {

	private VaadinProfileEditorViewLogic viewLogic;

	@Mock
	private VaadinProfileEditorView viewMock;
	@Mock
	private FieldGroup fieldGroupMock;
	@Mock
	private TextField firstNameFieldMock;
	@Mock
	private TextField lastNameFieldMock;
	@Mock
	private TextField emailAddressFieldMock;
	@Mock
	private DateField dateOfBirthFieldMock;
	@Mock
	private Button saveButtonMock;
	@Mock
	private Button closeButtonMock;
	@Mock
	private Event<SaveUserEvent> saveUserEventMock;
	@Mock
	private Event<CancelEditingEvent> cancelEditingEventMock;

	private ClickListener saveButtonClickListener;
	private ClickListener closeButtonClickListener;

	@Before
	public void setup() throws Exception {

		Mockito.when(this.viewMock.getFirstNameField()).thenReturn(this.firstNameFieldMock);
		Mockito.when(this.viewMock.getLastNameField()).thenReturn(this.lastNameFieldMock);
		Mockito.when(this.viewMock.getEmaiAddressField()).thenReturn(this.emailAddressFieldMock);
		Mockito.when(this.viewMock.getDateOfBirth()).thenReturn(this.dateOfBirthFieldMock);
		Mockito.when(this.viewMock.getSaveButton()).thenReturn(this.saveButtonMock);
		Mockito.when(this.viewMock.getCloseButton()).thenReturn(this.closeButtonMock);

		this.viewLogic = new VaadinProfileEditorViewLogic(this.viewMock, this.fieldGroupMock);
		this.viewLogic.cancelEditingEvent = this.cancelEditingEventMock;
		this.viewLogic.saveUserEvent = this.saveUserEventMock;

		Mockito.verify(this.fieldGroupMock).bind(this.firstNameFieldMock, "firstName");
		Mockito.verify(this.fieldGroupMock).bind(this.lastNameFieldMock, "lastName");
		Mockito.verify(this.fieldGroupMock).bind(this.emailAddressFieldMock, "emailAddress");
		Mockito.verify(this.fieldGroupMock).bind(this.dateOfBirthFieldMock, "dateOfBirth");

		ArgumentCaptor<ClickListener> saveListenerCaptor = ArgumentCaptor.forClass(ClickListener.class);
		Mockito.verify(this.saveButtonMock).addClickListener(saveListenerCaptor.capture());
		this.saveButtonClickListener = saveListenerCaptor.getValue();

		ArgumentCaptor<ClickListener> closeListenerCaptor = ArgumentCaptor.forClass(ClickListener.class);
		Mockito.verify(this.closeButtonMock).addClickListener(closeListenerCaptor.capture());
		this.closeButtonClickListener = closeListenerCaptor.getValue();
	}

	@Test
	public void shouldDiscardFieldBindingOnSetUserToEdit() {
		this.viewLogic.setUserToEdit(null);

		Mockito.verify(this.fieldGroupMock).discard();
	}

	@Test
	public void testSuccessfulSave() throws CommitException {
		User user = new User();

		this.viewLogic.setUserToEdit(user);

		this.saveButtonClickListener.buttonClick(new ClickEvent(Mockito.mock(Component.class)));

		Mockito.verify(this.fieldGroupMock).commit();

		ArgumentCaptor<SaveUserEvent> captor = ArgumentCaptor.forClass(SaveUserEvent.class);
		Mockito.verify(this.saveUserEventMock).fire(captor.capture());
		assertSame(user, captor.getValue().getUser());
	}

	@Test
	public void testCommitExceptionOnSave() throws CommitException {
		User user = new User();

		this.viewLogic.setUserToEdit(user);

		PowerMockito.mockStatic(Notification.class);
		Mockito.doThrow(new CommitException()).when(this.fieldGroupMock).commit();

		this.saveButtonClickListener.buttonClick(new ClickEvent(Mockito.mock(Component.class)));

		PowerMockito.verifyStatic();
		Notification.show(Mockito.anyString(), Mockito.anyString(), Mockito.any(Type.class));
	}

	@Test
	public void testCloseEditor() {
		this.closeButtonClickListener.buttonClick(new ClickEvent(Mockito.mock(Component.class)));

		Mockito.verify(this.fieldGroupMock).discard();
		Mockito.verify(this.cancelEditingEventMock).fire(Mockito.any(CancelEditingEvent.class));
	}

	@Test
	public void canGetViewAsComponent() {
		Component viewAsComponent = this.viewLogic.getViewAs(Component.class);

		assertSame(this.viewMock, viewAsComponent);
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
