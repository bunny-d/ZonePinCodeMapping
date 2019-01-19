package SpringBase;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Publisher {

    @Autowired
    private RabbitTemplate rt;

    public void sendToQueue(HashMap<Integer, List<String>> zoneWiseQueueData) {
        List<String> indexedData;

        for(Map.Entry<Integer, List<String>> entry : zoneWiseQueueData.entrySet()) {
            indexedData = entry.getValue();

            MessageProperties props = MessagePropertiesBuilder.newInstance()
                    .setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
                    .setMessageId(entry.getKey().toString())
                    .build();

            for(String data : indexedData) {
                Message message = MessageBuilder.withBody(data.getBytes())
                        .andProperties(props)
                        .build();
                rt.convertAndSend(data);
            }
        }
    }
}