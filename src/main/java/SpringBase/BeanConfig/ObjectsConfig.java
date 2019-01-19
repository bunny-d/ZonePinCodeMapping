package SpringBase.BeanConfig;

import SpringBase.Zones;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan("SpringBase")
@EnableAspectJAutoProxy
public class ObjectsConfig {

    @Bean
    public Zones zones() {
        return new Zones();
    }
}
