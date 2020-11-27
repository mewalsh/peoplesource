package cloud.mtech.peoplesource.exception;

import static java.util.stream.Collectors.toList;

import cloud.mtech.peoplesource.model.ApiError;
import com.networknt.schema.ValidationMessage;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.ValidationException;
import org.apache.camel.component.jsonvalidator.JsonValidationException;
import org.springframework.stereotype.Component;

@Component("exceptionHandler")
@Slf4j
public class ExceptionHandler {

    public ApiError handleJsonValidationException(JsonValidationException e) {
        log.debug("Validation Error", e);

        List<String> errors = e.getErrors().stream()
          .map(ValidationMessage::getMessage)
          .collect(toList());

        return new ApiError(e.getMessage(), errors);
    }

    public ApiError handleValidationException(ValidationException e) {
        log.debug("Validation Error", e);

        return new ApiError(e.getMessage());
    }

    public ApiError handleInternalException(Exception e) {
        log.warn("Unexpected Error", e);

        return new ApiError("An unexpected exception has occurred.");
    }
}
