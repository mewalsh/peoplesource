package cloud.mtech.peoplesource.routes.handlers;

import static org.apache.camel.Exchange.HTTP_RESPONSE_CODE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import cloud.mtech.peoplesource.model.ApiError;
import cloud.mtech.peoplesource.model.UserRequest;
import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.MockEndpoints;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.MethodMode;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest
@MockEndpoints
@DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
public class CreateUserRouteTest {

    @Produce
    private ProducerTemplate producerTemplate;

    @EndpointInject(uri = "mock:json-validator:schemas/in/user-schema.json")
    private MockEndpoint mockSchemaValidatorInbound;
    @EndpointInject(uri = "mock:json-validator:schemas/out/create-user-schema.json")
    private MockEndpoint mockSchemaValidatorOutbound;

    @EndpointInject(uri = "mock:direct:create-user-request")
    private MockEndpoint mockCreateUserRequest;

    @EndpointInject(uri = "mock:{{route.create-user.to}}")
    private MockEndpoint mockDispatchUserRequest;


    @Test
    public void createUser() throws InterruptedException {
        UserRequest expected = new UserRequest("foobar");

        String payload = "{ \"firstName\": \"foo\", \"lastName\": \"bar\" }";

        mockDispatchUserRequest.expectedBodiesReceived(expected);

        producerTemplate.requestBody("{{route.create-user.from}}", payload);

        mockDispatchUserRequest.assertIsSatisfied();
    }

    @Test
    public void createUser_inboundSchemaError() throws InterruptedException {
        String payload = "{ \"firstName\": \"\", \"lastName\": \"bar\" }";

        mockSchemaValidatorInbound.expectedBodiesReceived(payload);

        Exchange exchange = producerTemplate.request("{{route.create-user.from}}", ex -> ex.getIn().setBody(payload));
        Message response = exchange.getIn();
        ApiError body = response.getBody(ApiError.class);

        mockSchemaValidatorInbound.assertIsSatisfied();

        assertEquals(BAD_REQUEST.value(), response.getHeader(HTTP_RESPONSE_CODE));
        assertThat(body.getMessage(), containsString("JSon validation error with 1 errors."));
        assertThat(body.getErrors(), contains("$.firstName: must be at least 1 characters long"));
    }

    @Test
    public void createUser_outboundSchemaError() throws InterruptedException {
        String payload = "{ }";

        mockSchemaValidatorOutbound.expectedBodiesReceived(payload);

        Exchange exchange = producerTemplate.request("direct:create-user-request", ex -> ex.getIn().setBody(payload));
        Message response = exchange.getIn();
        ApiError body = response.getBody(ApiError.class);

        mockSchemaValidatorOutbound.assertIsSatisfied();

        assertEquals(INTERNAL_SERVER_ERROR.value(), response.getHeader(HTTP_RESPONSE_CODE));
        assertThat(body.getMessage(), containsString("An unexpected exception has occurred."));
    }
}