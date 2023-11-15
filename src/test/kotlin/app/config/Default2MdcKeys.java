package app.config;

import jakarta.servlet.http.HttpServletRequest;
import no.telenor.kt.boot3.configurable.mdc.KeyRegistry;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Default2MdcKeys {
    public Default2MdcKeys(KeyRegistry registry) {
        // noinspection DataFlowIssue
        registry.define("java.defaultKey", (context) -> ((HttpServletRequest) context.get("request")).getHeader("x-request-id"));
    }
}
