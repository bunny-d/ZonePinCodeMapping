package SpringBase.BeanConfig;

import SpringBase.SimpleMessageListener;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;

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
    public RabbitAdmin getRabbitAdmin() {
        RabbitAdmin admin = new RabbitAdmin(connectionFactory());
        Queue queue = getQueue();
        DirectExchange exchange = getExchange();
        Binding binding = zonePincodeBinding(exchange, queue);

        admin.declareQueue(queue);
        admin.declareExchange(exchange);
        admin.declareBinding(binding);

        return admin;
    }

    @Bean
    public RabbitTemplate getTemplate() {
        RabbitTemplate rt = new RabbitTemplate(connectionFactory());
        rt.setExchange("x_zonePincode");
        rt.setRoutingKey("mapping");
        return rt;
    }

    @Bean
    public SimpleMessageListenerContainer pojoListener(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setQueueNames("zonePincode");

        //MessageListenerAdapter listener = new MessageListenerAdapter(new Subscriber());
        //listener.setDefaultListenerMethod("receiveFromQueue");
        container.setMessageListener(getSimpleMessageListener());
        container.setPrefetchCount(2);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);

        return container;
    }

    @Bean
    public SimpleMessageListener getSimpleMessageListener() {
        return new SimpleMessageListener();
    }

}