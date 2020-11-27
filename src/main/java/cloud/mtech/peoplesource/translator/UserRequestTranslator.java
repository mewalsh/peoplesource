package cloud.mtech.peoplesource.translator;

import static java.lang.String.format;

import cloud.mtech.peoplesource.model.User;
import cloud.mtech.peoplesource.model.UserRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Handler;
import org.springframework.stereotype.Component;

@Component("userRequestTranslator")
@Slf4j
public class UserRequestTranslator {

    @Handler
    public UserRequest userRequest(User user) {
        String username = buildUsername(user);
        return new UserRequest(username);
    }

    private String buildUsername(User user) {
        return format("%.4s%.4s", user.getFirstName(), user.getLastName()).toLowerCase();
    }
}
