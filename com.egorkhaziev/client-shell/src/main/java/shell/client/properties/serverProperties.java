package shell.client.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "integrations.server")
@Data
public class serverProperties {

        private String url;
        private Integer connectTimeout;
        private Integer readTimeout;
        private Integer writeTimeout;

}
