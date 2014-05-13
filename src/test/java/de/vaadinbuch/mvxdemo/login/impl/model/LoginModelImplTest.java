package de.vaadinbuch.mvxdemo.login.impl.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import de.vaadinbuch.mvxdemo.domain.User;
import de.vaadinbuch.mvxdemo.domain.UserService;

@RunWith(value = MockitoJUnitRunner.class)
public class LoginModelImplTest {

    private LoginModelImpl model;

    @Mock
    private UserService userServiceMock;

    @Before
    public void setup() {
        this.model = new LoginModelImpl(this.userServiceMock);
    }

    @Test(expected = NullPointerException.class)
    public void cannotCreateWithoutUserService() {
        new LoginModelImpl(null);
    }

    @Test
    public void shouldProvideUserIdMinLength() {
        Mockito.when(this.userServiceMock.getUserIdMinLength()).thenReturn(42);
        int userIdMinLength = this.model.getMinUserIdLength();

        assertEquals(42, userIdMinLength);
    }

    @Test
    public void shouldProvidePasswordMinLength() {
        Mockito.when(this.userServiceMock.getPasswordMinLength()).thenReturn(666);
        int passwordMinLength = this.model.getMinPasswordLength();

        assertEquals(666, passwordMinLength);
    }

    @Test
    public void shouldReturnTrueOnSuccessfulLogin() {
        String userId = "zaphod";
        String password = "babelfish";

        Mockito.when(this.userServiceMock.checkCredentials(userId, password)).thenReturn(true);
        Mockito.when(this.userServiceMock.getUser(userId)).thenReturn(new User());

        boolean result = this.model.loginUser(userId, password);

        assertTrue(result);
    }

    @Test
    public void shouldReturnFalseOnFailedLogin() {
        String userId = "zaphod";
        String password = "babelfish";

        Mockito.when(this.userServiceMock.checkCredentials(userId, password)).thenReturn(false);

        boolean result = this.model.loginUser(userId, password);

        assertFalse(result);
    }
}
