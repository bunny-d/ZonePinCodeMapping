package SpringBase.BeanConfig;

import SpringBase.Zones;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("SpringBase")
public class ObjectsConfig {

    @Bean
    public Zones zones() {
        return new Zones();
    }
}
