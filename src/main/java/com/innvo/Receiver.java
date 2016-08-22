package com.innvo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;

import org.drools.core.event.DebugProcessEventListener;
import org.json.JSONException;
import org.json.JSONObject;
import org.kie.api.KieServices;
import org.kie.api.event.rule.DebugAgendaEventListener;
import org.kie.api.event.rule.DebugRuleRuntimeEventListener;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.innvo.handlers.GetEventsHandler;

@Component
public class Receiver {

    /**
     * Get a copy of the application context
     */
    @Autowired
    ConfigurableApplicationContext context;

    /**
     * When you receive a message, print it out, then shut down the application.
     * Finally, clean up any ActiveMQ server stuff.
     */
    @JmsListener(destination = "DemoQueue")
   
    public void receiveMessage(String message) throws JMSException, JSONException, IOException{
        System.out.println("Received <" + message + ">");
		String category = null;
		String subCategory = null;
		String subtype = null;
		String type = null;
		String name = null;
		JSONObject jsonObject = new JSONObject(message);
		category = jsonObject.get("category").toString();
		subCategory = jsonObject.get("subcategory").toString();
		subtype = jsonObject.get("subtype").toString();
		type = jsonObject.get("type").toString();
		name = jsonObject.get("name").toString();
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks.getKieClasspathContainer();
		KieSession kSession = kContainer.newKieSession("ksession-process");
		kSession.getWorkItemManager().registerWorkItemHandler("GetEventsHandler", new GetEventsHandler());
		kSession.getWorkItemManager().registerWorkItemHandler("SaveScore", new SaveScore());
		kSession.addEventListener(new DebugProcessEventListener());
		kSession.addEventListener(new DebugAgendaEventListener());
		kSession.addEventListener(new DebugRuleRuntimeEventListener());
		ks.getLoggers().newFileLogger(kSession, "./workflowlog");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("alertmessage", message);
		params.put("category", category);
		params.put("subcategory", subCategory);
		params.put("subtype", subtype);
		params.put("type", type);
		params.put("name", name);
		params.put("gatewayhostname", "localhost:8099");
		String emptyString="";
		if (message != null) {
			emptyString = message.toString();
		}
		params.put("noalertmessage", emptyString);
		kSession.startProcess("com.innvo", params);
		kSession.fireAllRules();
    }
}
