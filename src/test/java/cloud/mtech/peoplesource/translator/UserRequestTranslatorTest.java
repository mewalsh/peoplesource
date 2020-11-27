package cloud.mtech.peoplesource.translator;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import cloud.mtech.peoplesource.model.User;
import cloud.mtech.peoplesource.model.UserRequest;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.MockEndpoints;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(CamelSpringBootRunner.class)
public class UserRequestTranslatorTest {

    @Mock
    private User user;

    private UserRequestTranslator translator;

    @Before
    public void setup() {
        translator = new UserRequestTranslator();
    }

    @Test
    public void buildUserRequest() {
        when(user.getFirstName()).thenReturn("foo");
        when(user.getLastName()).thenReturn("bar");

        UserRequest expected = new UserRequest("foobar");
        UserRequest actual = translator.userRequest(user);

        assertEquals(expected, actual);
    }

    @Test
    public void buildUserRequestTruncatesFirstname() {
        when(user.getFirstName()).thenReturn("foo123");
        when(user.getLastName()).thenReturn("bar");

        UserRequest expected = new UserRequest("foo1bar");
        UserRequest actual = translator.userRequest(user);

        assertEquals(expected, actual);
    }

    @Test
    public void buildUserRequestTruncatesLastname() {
        when(user.getFirstName()).thenReturn("foo");
        when(user.getLastName()).thenReturn("bar123");

        UserRequest expected = new UserRequest("foobar1");
        UserRequest actual = translator.userRequest(user);

        assertEquals(expected, actual);
    }

    @Test
    public void buildUserRequestUsernameLowercase() {
        when(user.getFirstName()).thenReturn("FoO");
        when(user.getLastName()).thenReturn("BaR");

        UserRequest expected = new UserRequest("foobar");
        UserRequest actual = translator.userRequest(user);

        assertEquals(expected, actual);
    }
}