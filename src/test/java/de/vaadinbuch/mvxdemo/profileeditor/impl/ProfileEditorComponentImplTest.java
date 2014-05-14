package de.vaadinbuch.mvxdemo.profileeditor.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import javax.enterprise.event.Event;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.vaadin.ui.Component;

import de.vaadinbuch.mvxdemo.domain.User;
import de.vaadinbuch.mvxdemo.profileeditor.ProfileEditorModel;
import de.vaadinbuch.mvxdemo.profileeditor.ProfileEditorModel.ProfileSaveFailedException;
import de.vaadinbuch.mvxdemo.profileeditor.ProfileEditorView;
import de.vaadinbuch.mvxdemo.profileeditor.event.SaveFailedEvent;
import de.vaadinbuch.mvxdemo.profileeditor.event.SaveSuccessEvent;
import de.vaadinbuch.mvxdemo.profileeditor.event.StopEditingEvent;
import de.vaadinbuch.mvxdemo.profileeditor.impl.event.CancelEditingEvent;
import de.vaadinbuch.mvxdemo.profileeditor.impl.event.SaveUserEvent;

@RunWith(value = MockitoJUnitRunner.class)
public class ProfileEditorComponentImplTest {

    private ProfileEditorComponentImpl editorComponent;

    @Mock
    private ProfileEditorView viewMock;
    @Mock
    private ProfileEditorModel modelMock;
    @Mock
    private Event<SaveSuccessEvent> saveSuccessEventMock;
    @Mock
    private Event<SaveFailedEvent> saveFailedEventMock;
    @Mock
    private Event<StopEditingEvent> stopEditingEventMock;

    @Before
    public void setup() {
        this.editorComponent = new ProfileEditorComponentImpl(this.modelMock, this.viewMock);
        this.editorComponent.saveFailedEvent = this.saveFailedEventMock;
        this.editorComponent.saveSuccessEvent = this.saveSuccessEventMock;
        this.editorComponent.stopEditingEvent = this.stopEditingEventMock;
    }

    @Test
    public void shouldSendEventOnStopEditing() {
        this.editorComponent.cancelEditing(new CancelEditingEvent(this.viewMock));

        Mockito.verify(this.stopEditingEventMock).fire(Mockito.any(StopEditingEvent.class));
    }

    @Test
    public void shouldDelegateViewAccessToView() {
        Class<?> type = Component.class;

        this.editorComponent.getViewAs(type);

        Mockito.verify(this.viewMock).getViewAs(type);
    }

    @Test
    public void shouldSetUserToEditFromModelAtView() {
        String userId = "zaphod";

        User user = new User();

        Mockito.when(this.modelMock.getUser(userId)).thenReturn(user);

        this.editorComponent.editProfileUser(userId);

        Mockito.verify(this.viewMock).setUserToEdit(user);
    }

    @Test
    public void testSuccessfulSave() throws ProfileSaveFailedException {
        String userId = "zaphod";
        User user = new User();
        user.setUserId(userId);

        this.editorComponent.saveUser(new SaveUserEvent(this.viewMock, user));

        Mockito.verify(this.modelMock).saveUser(user);

        ArgumentCaptor<SaveSuccessEvent> captor = ArgumentCaptor.forClass(SaveSuccessEvent.class);
        Mockito.verify(this.saveSuccessEventMock).fire(captor.capture());
        assertEquals(user.getUserId(), captor.getValue().getUserId());
    }

    @Test
    public void testFailedSave() throws ProfileSaveFailedException {
        String userId = "zaphod";
        User user = new User();
        user.setUserId(userId);

        ProfileSaveFailedException exception = new ProfileSaveFailedException("möp!");

        Mockito.doThrow(exception).when(this.modelMock).saveUser(user);

        this.editorComponent.saveUser(new SaveUserEvent(this.viewMock, user));

        ArgumentCaptor<SaveFailedEvent> captor = ArgumentCaptor.forClass(SaveFailedEvent.class);
        Mockito.verify(this.saveFailedEventMock).fire(captor.capture());
        assertSame(exception, captor.getValue().getCause());
    }
}
