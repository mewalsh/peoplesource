package cloud.mtech.peoplesource.routes.api;

import static org.springframework.http.HttpStatus.CREATED;

import cloud.mtech.peoplesource.routes.BaseRouteBuilder;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

@Component
public class UserApi extends BaseRouteBuilder {

    @Override
    public void configure() {
        super.configure();

        rest("/api")
          .post("/users")
          .id("api-create-user")
          .description("Create a new user")
          .route()
          .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(CREATED.value()))
          .to("{{api.user.create.to}}");
    }
}
