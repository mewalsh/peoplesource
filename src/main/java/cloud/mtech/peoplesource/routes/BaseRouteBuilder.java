package cloud.mtech.peoplesource.routes;

import org.apache.camel.Exchange;
import org.apache.camel.ValidationException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Component
public class BaseRouteBuilder extends RouteBuilder {

    @Override
    public void configure() {

        onException(ValidationException.class)
          .handled(true)
          .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(400))
          .transform(exchangeProperty(Exchange.EXCEPTION_CAUGHT))
          .bean("exceptionHandler", "handleValidationException")
          .marshal().json(JsonLibrary.Jackson);

        onException(Exception.class)
          .handled(true)
          .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(500))
          .transform(exchangeProperty(Exchange.EXCEPTION_CAUGHT))
          .bean("exceptionHandler", "handleInternalException")
          .marshal().json(JsonLibrary.Jackson);

    }
}
