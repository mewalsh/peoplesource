package cloud.mtech.peoplesource.routes.handlers;

import static org.apache.camel.model.dataformat.JsonLibrary.Jackson;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import cloud.mtech.peoplesource.model.User;
import cloud.mtech.peoplesource.routes.BaseRouteBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.component.jsonvalidator.JsonValidationException;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Component
public class CreateUserRoute extends BaseRouteBuilder {

    @Override
    public void configure() {
        super.configure();

        from("{{route.create-user.from}}")
          .doTry()
          .to("json-validator:schemas/in/user-schema.json")
          .bean("userRequestTranslator")
          .marshal().json(Jackson)
          .to("direct:create-user-request")
          .doCatch(JsonValidationException.class)
          .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(BAD_REQUEST.value()))
          .transform(exchangeProperty(Exchange.EXCEPTION_CAUGHT))
          .bean("exceptionHandler", "handleJsonValidationException")
          .marshal().json(Jackson)
          .end();

        from("direct:create-user-request")
          .doTry()
          .to("json-validator:schemas/out/create-user-schema.json")
          .to("{{route.create-user.to}}")
          .doCatch(JsonValidationException.class)
          .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(INTERNAL_SERVER_ERROR.value()))
          .transform(exchangeProperty(Exchange.EXCEPTION_CAUGHT))
          .bean("exceptionHandler", "handleInternalException")
          .marshal().json(Jackson)
          .end();
    }
}
