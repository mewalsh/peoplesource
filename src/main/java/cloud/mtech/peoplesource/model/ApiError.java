package cloud.mtech.peoplesource.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class ApiError {
    private final String message;
    private List<String> errors;
}