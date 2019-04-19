import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * Created by Administrator
 * 2019-04-19
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext-ActiveMQ.xml")
public class SpringActiveMQTest {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private ActiveMQQueue activeMQQueue;

    @Autowired
    private ActiveMQTopic activeMQTopic;

    @Test
    public void testQueueProducer() {
        jmsTemplate.send(activeMQQueue, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage("spring activemq queue test");
                return textMessage;
            }
        });
    }

    @Test
    public void testTopicProducer() {
        jmsTemplate.send(activeMQTopic, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage("spring activemq topic test");
                return textMessage;
            }
        });
    }

    @Test
    public void testQueueConsumer() throws Exception {
        //等待
        System.in.read();
    }

}
