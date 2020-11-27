package cloud.mtech.peoplesource.config;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestConfig extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        restConfiguration()
          .port(8080)
          .enableCORS(true)
          .apiContextPath("/api-docs")
          .apiProperty("api.title", "Test REST API")
          .apiProperty("api.version", "v1")
          .apiProperty("cors", "true")
          .apiContextRouteId("doc-api")
          .component("servlet")
          .dataFormatProperty("prettyPrint", "true");
    }
}
