package cloud.mtech.peoplesource.routes.mock;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.MethodMode;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest
@DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
public class MockUserServiceRouteTest {

    @Produce(uri = "{{route.user-service.create-user.from}}")
    private ProducerTemplate endpoint;

    @Test
    public void testUsernameGenerated() throws Exception {
        String expected = "{ \"message\": \"User Created\", \"username\": \"test\" }";

        String payload = "{ \"username\": \"test\" }";
        String actual = endpoint.requestBody((Object) payload, String.class);

        JSONAssert.assertEquals(expected, actual, true);
    }

}