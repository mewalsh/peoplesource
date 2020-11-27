package cloud.mtech.peoplesource.config;

import static org.apache.camel.component.jackson.JacksonConstants.ENABLE_TYPE_CONVERTER;
import static org.apache.camel.component.jackson.JacksonConstants.TYPE_CONVERTER_TO_POJO;

import java.util.Map;
import org.apache.camel.CamelContext;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CamelConfig {

    private static final String ENABLE = "true";

    @Bean
    CamelContextConfiguration contextConfiguration() {
        return new CamelContextConfiguration() {
            @Override
            public void beforeApplicationStart(CamelContext context) {
                Map<String, String> globalOptions = context.getGlobalOptions();
                globalOptions.put(ENABLE_TYPE_CONVERTER, ENABLE);
                globalOptions.put(TYPE_CONVERTER_TO_POJO, ENABLE);
            }

            @Override
            public void afterApplicationStart(CamelContext camelContext) {
            }
        };
    }

}
