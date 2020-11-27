package cloud.mtech.peoplesource.routes.api;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;

import cloud.mtech.peoplesource.model.ApiError;
import cloud.mtech.peoplesource.model.User;
import cloud.mtech.peoplesource.model.UserResponse;
import io.swagger.annotations.Api;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UserApiIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void createUser() {
        UserResponse expected = new UserResponse("User Created", "foobar");

        User user = new User("foo", "bar");
        ResponseEntity<UserResponse> response = restTemplate.postForEntity("/api/users", user, UserResponse.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expected, response.getBody());
    }

    @Test
    public void createUserEmptyFirstName() {
        User user = new User("", "bar");
        ResponseEntity<ApiError> response = restTemplate.postForEntity("/api/users", user, ApiError.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        ApiError actual = response.getBody();
        assertThat(actual.getMessage(), containsString("JSon validation error with 1 errors."));
        assertThat(actual.getErrors(), contains("$.firstName: must be at least 1 characters long"));
    }
}