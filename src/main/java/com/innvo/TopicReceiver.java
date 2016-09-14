package com.innvo;

import java.io.IOException;
import java.text.ParseException;

import javax.jms.JMSException;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class TopicReceiver {
	
	private final Logger log = LoggerFactory.getLogger(TopicReceiver.class);
	@Autowired
	ConfigurableApplicationContext context;
	
	@JmsListener(destination = "AlertMessageQueue")
	//below method will trigger whenever a alert message is received in queue. 
	public void receiveMessage(String message) throws JMSException, JSONException, IOException, ParseException, RESTClientException {
		log.debug("TopicReceiver----Received <" + message + ">");
		log.debug("*****-----Mutiple alert message using ActiveMQ Topic----******");
		}
}
