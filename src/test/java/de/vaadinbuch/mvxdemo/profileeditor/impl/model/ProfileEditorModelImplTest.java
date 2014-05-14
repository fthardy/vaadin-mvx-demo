package de.vaadinbuch.mvxdemo.profileeditor.impl.model;

import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import de.vaadinbuch.mvxdemo.domain.User;
import de.vaadinbuch.mvxdemo.domain.UserService;
import de.vaadinbuch.mvxdemo.profileeditor.ProfileEditorModel.ProfileSaveFailedException;

@RunWith(value = MockitoJUnitRunner.class)
public class ProfileEditorModelImplTest {

    private ProfileEditorModelImpl model;

    @Mock
    private UserService userServiceMock;

    @Before
    public void setup() throws Exception {
        this.model = new ProfileEditorModelImpl(this.userServiceMock);
    }

    @Test
    public void shouldGetUserFromService() {
        String userId = "zaphod";

        User user = new User();

        Mockito.when(this.userServiceMock.getUser(userId)).thenReturn(user);

        User result = this.model.getUser(userId);

        assertSame(user, result);
    }

    @Test
    public void shouldStoreUserAtService() throws ProfileSaveFailedException {
        User user = new User();

        this.model.saveUser(user);

        Mockito.verify(this.userServiceMock).storeUser(user);
    }

    @Test(expected = ProfileSaveFailedException.class)
    public void shouldThrowExceptionOnStoreFailure() throws ProfileSaveFailedException {
        User user = new User();

        Mockito.doThrow(new RuntimeException()).when(this.userServiceMock).storeUser(user);

        this.model.saveUser(user);
    }
}
