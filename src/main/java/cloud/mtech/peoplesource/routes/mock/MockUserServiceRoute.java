package cloud.mtech.peoplesource.routes.mock;

import cloud.mtech.peoplesource.model.UserRequest;
import cloud.mtech.peoplesource.model.UserResponse;
import cloud.mtech.peoplesource.routes.BaseRouteBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Component
public class MockUserServiceRoute extends BaseRouteBuilder {

    @Override
    public void configure() {
        from("{{route.user-service.create-user.from}}")
          .unmarshal().json(JsonLibrary.Jackson, UserRequest.class)
          .process(this::createUser)
          .marshal().json(JsonLibrary.Jackson);
    }

    private void createUser(Exchange exchange) {
        UserRequest request = exchange.getIn().getBody(UserRequest.class);
        UserResponse response = new UserResponse("User Created", request.getUsername());
        exchange.getIn().setBody(response);
    }
}
