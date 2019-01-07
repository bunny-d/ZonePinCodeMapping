package SpringBase.BeanConfig;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class RabbitConfig {

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setHost("localhost");
        return connectionFactory;
    }

    @Bean
    public DirectExchange getExchange() {
        return new DirectExchange("x_zonePincode");
    }

    @Bean
    public Queue getQueue() {
        return new Queue("zonePincode");
    }

    @Bean
    public Binding zonePincodeBinding(DirectExchange exchange, Queue queue) {
        return BindingBuilder.bind(queue).to(exchange).with("mapping");
    }

    @Bean
    public RabbitTemplate getTemplate() {
        RabbitTemplate rt = new RabbitTemplate(connectionFactory());
        return rt;
    }
}
