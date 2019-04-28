package com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing;

import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.infrastructure.route")
@SpringBootApplication
public class TicketEndPointApplication {

	/**
	 * main method
	 * @param args application arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(TicketEndPointApplication.class, args);
	}

	@Bean
	public ServletRegistrationBean camelServletRegistrationBean() {
		ServletRegistrationBean registration = new ServletRegistrationBean(new CamelHttpTransportServlet(), "/api/*");
		registration.setName("CamelServlet");
		return registration;
	}

}
