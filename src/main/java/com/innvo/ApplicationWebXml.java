package com.innvo;

import javax.jms.Queue;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;

/**
 * This is a helper Java class that provides an alternative to creating a web.xml.
 * This will be invoked only when the application is deployed to a servlet container like Tomcat, Jboss etc.
 */
@EnableJms
public class ApplicationWebXml extends SpringBootServletInitializer {
	
	@Bean
	public Queue queue() {
		return new ActiveMQQueue("DemoQueue");}

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        /**
         * set a default to use when no profile is configured.
         */
        AdapWorkflowApp.addDefaultProfile(application.application());
        return application.sources(AdapWorkflowApp.class);
    }
}
