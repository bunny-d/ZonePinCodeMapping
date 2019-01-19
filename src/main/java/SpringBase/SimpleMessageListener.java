package SpringBase;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SimpleMessageListener implements ChannelAwareMessageListener {
    @Autowired
    private ESIndex esIndex;

    List<String> combined = new ArrayList<String>();

    public void onMessage(Message message, Channel channel) throws Exception{
        String body = new String(message.getBody(),"UTF-8");

        if(_process(body)) {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } else {
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }

        int queuemessageSize = channel.queueDeclarePassive("zonePincode").getMessageCount();
        if(queuemessageSize == 0)
            System.exit(0);

    }

    public boolean _process(String body) {
        boolean result = true;
        this.combined.add(body);

        if(body.charAt(1) == 'p') {
            try {
                result = this.esIndex.indexDataOnES(this.combined);
            } catch(Exception e) {
                System.out.println(e);
            }
            this.combined.clear();
        }
        return result;
    }
}
